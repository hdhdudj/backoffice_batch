package io.spring.main;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class DataShareBean <T> {
    private Map<String, T> shareDataMap;

    public DataShareBean(){
        shareDataMap = new HashMap<String, T>();
    }

    public T getData(String key){
        if(shareDataMap == null){
            log.debug("shareDataMap is null.");
            return null;
        }
        return shareDataMap.get(key);
    }

    public int getSize(){
        if(this.shareDataMap == null){
            log.debug("Map is not initialized.");
            return 0;
        }
        return shareDataMap.size();
    }

    public void putData(String key, T data){
        if(shareDataMap == null){
            log.debug("Map is not initialized.");
            return;
        }
        shareDataMap.put(key,data);
    }
}
