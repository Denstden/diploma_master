package ua.kiev.unicyb.diploma.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantEntity;
import ua.kiev.unicyb.diploma.service.VariantService;

@RestController
@RequestMapping(value = "/variants")
@CrossOrigin
@Slf4j
public class VariantController {
    private final VariantService variantService;

    @Autowired
    public VariantController(final VariantService variantService) {
        this.variantService = variantService;
    }

    @RequestMapping(value = "/getVariant", method = RequestMethod.GET)
    public VariantEntity getVariant(@RequestParam(name = "testId") Long testId) {
        final VariantEntity variant = variantService.getVariant(testId);
        return variant;
    }
}
