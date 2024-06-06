package com.example.ISA2023.back.congifs;
import com.example.ISA2023.back.services.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CacheConfig implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    private final CompanyService companyService;

    public CacheConfig(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(".... Fetching books");
        logger.info("Cache call 1 -->" + companyService.getAllCached().toString());
        logger.info("Cache call 2 -->" + companyService.getAllCached().toString());
        logger.info("Cache call 3 -->" + companyService.getAllCached().toString());
        logger.info("Cache call 4 -->" + companyService.getAllCached().toString());
        logger.info("Cache call 5 -->" + companyService.getAllCached().toString());
        logger.info("Cache call 6 -->" + companyService.getAllCached().toString());
    }

}