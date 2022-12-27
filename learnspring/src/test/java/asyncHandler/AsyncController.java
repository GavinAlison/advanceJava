package asyncHandler;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class AsyncController {

    @Test
    public void req(){
        RestTemplate restTemplate = new RestTemplate();
        String resObj = restTemplate.getForObject("http://0.0.0.0:8082/async", String.class);
        log.info("result: {}", resObj);
    }
}
