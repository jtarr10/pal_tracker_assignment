package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    @Value("${port:NOT_SET}")
    private String PORT;
    @Value("${memory.limit:NOT_SET}")
    private String MEMORY_LIMIT;
    @Value("${cf.instance.index:NOT_SET}")
    private String CF_INSTANCE_INDEX;
    @Value("${cf.instance.addr:NOT_SET}")
    private String CF_INSTANCE_ADDR;

    public EnvController() {}

    public EnvController(String port, String memory_limit, String cf_instance_index, String cf_instance_addr){
        PORT = port;
        MEMORY_LIMIT = memory_limit;
        CF_INSTANCE_INDEX = cf_instance_index;
        CF_INSTANCE_ADDR = cf_instance_addr;
    }


    @GetMapping("/env")
    public Map<String,String> getEnv(){
        Map<String,String> response = new HashMap<String, String>();
        response.put("PORT", PORT);
        response.put("MEMORY_LIMIT", MEMORY_LIMIT);
        response.put("CF_INSTANCE_INDEX", CF_INSTANCE_INDEX);
        response.put("CF_INSTANCE_ADDR", CF_INSTANCE_ADDR);

        return response;
    }
}
