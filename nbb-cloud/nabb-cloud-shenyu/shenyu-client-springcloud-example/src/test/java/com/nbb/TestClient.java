package com.nbb;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestClient {

    public static void main(String[] args) {
        try {
            try {
                try {
                    int a = 10 /0;
                } catch (Exception e) {
                    throw new IllegalArgumentException("111111111111", e);
                }
            } catch (Exception e) {
                throw new RuntimeException("222222222222", e);
            }
        } catch (Exception e) {
            log.error("333333333333", e);
        }

    }
}
