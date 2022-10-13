package org.goormton.darktourism;

import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.goormton.darktourism.domain.*;
import org.goormton.darktourism.repository.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
//@Profile("!test & !prod")
public class PreProcessorDev {

    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;
    private final PlaceStarMemberRepository placeStarMemberRepository;
    private final PlaceImageUrlRepository placeImageUrlRepository;
    private final BadgeRepository badgeRepository;
    
    @PostConstruct
    @Transactional
    public void setUp() {
        initData();
    }

    private void initData() {
        
        Member member = memberRepository.save(Member.createMember("98즈"));

        Badge save = badgeRepository.save(Badge.createBadge("3",
                "3개 방문 뺴찌",
                "https://darktourism.s3.ap-northeast-2.amazonaws.com/stamp_off/%E1%84%83%E1%85%A1%E1%84%85%E1%85%A1%E1%86%BC%E1%84%89%E1%85%B1.png",
                "https://darktourism.s3.ap-northeast-2.amazonaws.com/stamp_on/%E1%84%83%E1%85%A1%E1%84%85%E1%85%A1%E1%86%BC%E1%84%89%E1%85%B1_on.png"
        ));

        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader("./static/placedata.csv"))) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
            records.forEach(r -> r.forEach(System.out::println));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
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
}
