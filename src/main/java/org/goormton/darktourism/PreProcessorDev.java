package org.goormton.darktourism;

import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.goormton.darktourism.domain.badge.Badge;
import org.goormton.darktourism.domain.member.Member;
import org.goormton.darktourism.domain.member.MemberRole;
import org.goormton.darktourism.domain.place.Place;
import org.goormton.darktourism.domain.place.PlaceImageUrl;
import org.goormton.darktourism.repository.badge.BadgeRepository;
import org.goormton.darktourism.repository.member.MemberRepository;
import org.goormton.darktourism.repository.member.MemberRoleRepository;
import org.goormton.darktourism.repository.place.PlaceImageUrlRepository;
import org.goormton.darktourism.repository.place.PlaceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class PreProcessorDev {
    
    private final PlaceRepository placeRepository;
    private final PlaceImageUrlRepository placeImageUrlRepository;
    private final BadgeRepository badgeRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final MemberRepository memberRepository;

    @Value("${static.place-data-path}")
    private String PLACE_DATA;
//    private final static String BADGE_DATA = "static/data/badgedata.csv";
    
    @PostConstruct
    @Transactional
    public void setUp() {
        initData();
    }

    private void initData() {
        savePlaceData(readCSV(PLACE_DATA));
//        saveBadgeData(readCSV(BADGE_DATA));
        createMemberRole();
    }

    private void createMemberRole() {
        MemberRole role_member = new MemberRole("ROLE_MEMBER");
        MemberRole role_admin = new MemberRole("ROLE_ADMIN");
        memberRoleRepository.save(role_member);
        memberRoleRepository.save(role_admin);
        Member admin = Member.createMember("admin", role_admin);
        memberRepository.save(admin);
    }
    
    private void saveBadgeData(List<List<String>> records) {
        IntStream.range(1, records.size()).forEach(idx -> {
            List<String> record = records.get(idx);
            String name = record.get(0);
            String description = record.get(1);
            String afterImageUrl = record.get(2);
            String prevImageUrl = record.get(3);
            Badge badge = Badge.builder()
                    .name(name)
                    .description(description)
                    .orderNum(idx)
                    .badgePrevImageUrl(prevImageUrl)
                    .badgeAfterImageUrl(afterImageUrl)
                    .build();
            badgeRepository.save(badge);
        });
    }

    private void savePlaceData(List<List<String>> records) {
        records.subList(1, records.size())
                .forEach(record -> {
                    String name = record.get(0);
                    String shortDescription = record.get(1);
                    String description = record.get(2);
                    String source = record.get(3);
                    String address = record.get(4);
                    String placeImageUrl1 = record.get(5);
                    String placeImageUrl2 = record.get(6);
                    String stampOffUrl = record.get(7);
                    String stampOnUrl = record.get(8);
                    double latitude = Double.parseDouble(record.get(9));
                    double longitude = Double.parseDouble(record.get(10));
                    Place place = Place.createPlace(name, shortDescription, description, stampOffUrl, stampOnUrl, latitude, longitude, address, source);
                    PlaceImageUrl pi1 = PlaceImageUrl.createPlaceImageUrl(
                            place, placeImageUrl1, 1);
                    PlaceImageUrl pi2 = PlaceImageUrl.createPlaceImageUrl(
                            place, placeImageUrl2, 2);

                    placeImageUrlRepository.saveAll(place.getPlaceImageUrls());
                    place.addPlaceImageUrls(pi1);
                    place.addPlaceImageUrls(pi2);
                    placeRepository.save(place);
                });
    }

    private List<List<String>> readCSV(String path) {
        System.out.println("READ FILE PATH : " + path);
        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(path))) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
            records.forEach(r -> r.forEach(System.out::println));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }
}
