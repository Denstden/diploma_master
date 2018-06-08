package ua.kiev.unicyb.diploma.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.kiev.unicyb.diploma.domain.entity.configuration.ConfigurationEntity;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.exception.UploadFileException;
import ua.kiev.unicyb.diploma.service.ConfigurationService;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping(value = "/api/configurations")
@PreAuthorize("hasAuthority('TUTOR') or hasAuthority('ADMIN')")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @Autowired
    public ConfigurationController(final ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ConfigurationEntity> configurationList() {
        final String username = getCurrentUsername();
        return configurationService.configurationList(username);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public TestEntity loadConfiguration(@RequestParam(name = "id") Long configId) {
        return configurationService.loadConfiguration(configId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> loadFile(@RequestParam("file") MultipartFile file,
                                           @RequestParam(value = "configFileName", required = false) String configFileName) {
        try {
            final String username = getCurrentUsername();
            configurationService.loadFile(file, configFileName, username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UploadFileException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
        }
    }

    private String getCurrentUsername() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
