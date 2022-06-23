package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Query;
import com.epam.esm.exception.CustomInternalException;
import com.epam.esm.exception.CustomNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The Tag REST API controller.
 *
 * @author Maksim Rutkouski
 */
@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService certificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Get all certificates.
     *
     * @return all available certificates
     */
    @GetMapping
    public List<GiftCertificate> findAll() {
        return certificateService.findAll();
    }

    /**
     * Get a certificate by id.
     *
     * @param id the certificate id
     * @return found certificate, otherwise error response with 40401 status code
     */
    @GetMapping("/{id}")
    public GiftCertificate findById(@PathVariable("id") Long id) {
        return certificateService.findById(id).orElseThrow(() -> new CustomNotFoundException(id));
    }

    /**
     * Create a certificate.
     *
     * @param certificate the certificate json object
     * @return created certificate
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificate create(@RequestBody @Valid GiftCertificate certificate) {
        try {
            return certificateService.create(certificate);
        } catch (ServiceException e) {
            throw new CustomInternalException("The certificate can not be created at the moment");
        }
    }

    /**
     * Delete a certificate.
     *
     * @param id the certificate id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        certificateService.deleteById(id);
    }

    @PutMapping("/{id}")
    public GiftCertificate update(@PathVariable("id") Long id, @RequestBody GiftCertificate source) {
        try {
            return certificateService.update(id, source);
        } catch (ServiceException e) {
            throw new CustomNotFoundException(id);
        }
    }

    /**
     * Delete a certificate.
     *
     * @param tag request parameter for search by tag
     * @param partName the part of name/description of a certificate
     * @param sortByName allow certificate sorting by a Name
     * @param sortByDate allow certificate sorting by a Date
     */
    @GetMapping("/query")
    public List<GiftCertificate> findAllByParams(@RequestParam(value = "tag", required = false) String tag,
                                                 @RequestParam(value = "partName", required = false) String partName,
                                                 @RequestParam(value = "sortByName", required = false) String sortByName,
                                                 @RequestParam(value = "sortByDate",required = false) String sortByDate) {
        return certificateService.findAllByParams(new Query(tag, partName, sortByName, sortByDate));
    }

    /**
     * Delete all tags from the chosen certificate
     *
     * @param id the certificate id
     */
    @DeleteMapping("/{id}/tags")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearTags(@PathVariable("id") Long id) {
        certificateService.clearTags(id);
    }
}
