package com.sxy.learning.spring.soundsystem;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 扫描器
 */
@Configuration
@ComponentScan(basePackageClasses = {ScanFlag.class})
public class CDPlayerConfig {
}
