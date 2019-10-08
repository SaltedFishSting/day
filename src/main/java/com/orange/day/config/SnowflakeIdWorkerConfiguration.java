package com.orange.day.config;

import com.orange.day.util.SnowflakeIdWorker;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.Inet4Address;
import java.net.UnknownHostException;


@Configuration
public class SnowflakeIdWorkerConfiguration {

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker() {
        Long workerId;
        Long dataCenterId;
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int[] ints = StringUtils.toCodePoints(hostAddress);
            int sums = 0;
            for (int b : ints) {
                sums += b;
            }
            workerId = (long) (sums % 32);
        } catch (UnknownHostException e) {
            // 如果获取失败，则使用随机数备用
            workerId = RandomUtils.nextLong(0, 31);
        }
        int[] ints = StringUtils.toCodePoints(SystemUtils.getHostName());
        int sums = 0;
        for (int i : ints) {
            sums += i;
        }
        dataCenterId = (long) (sums % 32);
        return new SnowflakeIdWorker(workerId, dataCenterId);
    }
}
