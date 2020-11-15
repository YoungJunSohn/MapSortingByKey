package sorting;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Example {
    public static void main(String[] args) {
        SampleData sampleData = new SampleData();
        Map<String, String> keyValueMap = sampleData.getRedisKeyValue();

        List<String> sortedList = keyValueMap.keySet()
                .stream()
                .sorted((key1, key2) -> {
                    String[] key1Str = key1.split(":");
                    String[] key2Str = key2.split(":");

                    for (int i = 2; i < key1Str.length; i++) {
                        int compare = Integer.compare(Integer.parseInt(key1Str[i]), Integer.parseInt(key2Str[i]));

                        if(compare!=0){
                            return compare;
                        }
                    }
                    return 0;
                })
                .collect(Collectors.toList());

        for (String s : sortedList) {
            System.out.println(s);
        }

    }//main()

}

class SampleData {
    private Map<String, String> redisKeyValue = new HashMap<>();


    public String getDateFormat(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }


    public Map<String, String> getRedisKeyValue() {

        LocalDate startDate = LocalDate.of(2020, 11, 1);
        LocalDate endDate = startDate.plusMonths(1);

        for (int i = 0; i < 100; i++) { //순서 엉망인 샘플 데이터 100개만 만들어보자
            int randomClassId = (int) Math.ceil(Math.random() * 10000);
            int randomUserId = (int) Math.ceil(Math.random() * 10000);

            long generateRandom = new Random().nextInt(
                    (Math.toIntExact(endDate.toEpochDay())
                            - Math.toIntExact(startDate.toEpochDay() + 1)
                    ))
                    + Math.toIntExact(startDate.toEpochDay());

            LocalDate randomDate = LocalDate.ofEpochDay(generateRandom);

            String redisKey = "service:classtime:" +
                    +randomClassId +
                    ":" + randomUserId +
                    ":" + randomDate;
            String redisValue = "value";

            this.redisKeyValue.put(redisKey, redisValue);
        }//for
        return redisKeyValue;
    }
}
