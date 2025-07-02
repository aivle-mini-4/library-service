package aivle.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    
    private List<String> protectedPaths;
    private Map<String, List<String>> rolePaths; // role별 접근 가능한 경로
    
    public List<String> getProtectedPaths() {
        return protectedPaths;
    }
    
    public void setProtectedPaths(List<String> protectedPaths) {
        this.protectedPaths = protectedPaths;
    }
    
    public Map<String, List<String>> getRolePaths() {
        return rolePaths;
    }
    
    public void setRolePaths(Map<String, List<String>> rolePaths) {
        this.rolePaths = rolePaths;
    }
} 