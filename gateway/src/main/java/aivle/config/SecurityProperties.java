package aivle.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    
    private List<String> protectedPaths;
    
    public List<String> getProtectedPaths() {
        return protectedPaths;
    }
    
    public void setProtectedPaths(List<String> protectedPaths) {
        this.protectedPaths = protectedPaths;
    }
} 