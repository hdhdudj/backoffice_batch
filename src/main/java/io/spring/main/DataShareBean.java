package io.spring.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class DataShareBean <T> {
    private Map<String, T> shareDataMap;

    public DataShareBean(){
        shareDataMap = new ConcurrentHashMap<String, T>();
    }

    public void initShareDataMap(){
        this.shareDataMap = new ConcurrentHashMap<String, T>();
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

    public Map<String, T> getMap(){
        return this.shareDataMap;
    }
}
