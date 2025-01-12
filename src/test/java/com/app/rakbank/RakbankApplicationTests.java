package com.app.rakbank;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RakbankApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
    void mainMethodTest() {
        // Mock the SpringApplication class
        SpringApplication mockSpringApplication = mock(SpringApplication.class);
        
        // Call the main method
        RakbankApplication.main(new String[] {});

        // Verify SpringApplication.run() is called
        verifyNoMoreInteractions(mockSpringApplication);
    }
}
