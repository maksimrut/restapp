package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateServiceImplTest {

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private GiftCertificateRepositoryImpl certificateRepository;

    @InjectMocks
    private GiftCertificateServiceImpl certificateService;

    private List<GiftCertificate> giftCertificates;

    @BeforeAll
    void setUp() {
        giftCertificates = new ArrayList<>();

        GiftCertificate c1 = new GiftCertificate();
        c1.setId(1L);
        c1.setName("certificate1");
        c1.setDescription("description of c1");
        c1.setPrice(BigDecimal.valueOf(1.5));
        c1.setDuration(30);
        c1.setCreateDate(LocalDateTime.now().minusDays(2L));
        c1.setLastUpdateDate(LocalDateTime.now());

        GiftCertificate c2 = new GiftCertificate();
        c2.setId(2L);
        c2.setName("certificate2");
        c2.setDescription("description of c2");
        c2.setPrice(BigDecimal.valueOf(10));
        c2.setDuration(5);
        c2.setCreateDate(LocalDateTime.now().minusDays(5L));
        c2.setLastUpdateDate(LocalDateTime.now());

        GiftCertificate c3 = new GiftCertificate();
        c3.setId(3L);
        c3.setName("certificate3");
        c3.setDescription("description of c3");
        c3.setPrice(BigDecimal.valueOf(1.5));
        c3.setDuration(30);
        c3.setCreateDate(LocalDateTime.now());

        Tag testTag = new Tag();
        testTag.setId(1L);
        testTag.setName("test tagName");

        c1.getTags().add(testTag);
        giftCertificates.add(c1);
        giftCertificates.add(c2);
        giftCertificates.add(c3);
    }

    @Test
    void findAll() {
        Mockito.when(certificateRepository.findAll()).thenReturn(giftCertificates);
        List<GiftCertificate> actual = certificateService.findAll();
        assertEquals(giftCertificates, actual);
    }

    @Test
    void findById() {
        Mockito.when(certificateRepository.findById(Mockito.anyLong()))
                .thenAnswer(invocation -> giftCertificates.stream()
                        .filter(c -> c.getId().equals(invocation.getArgument(0))).findAny());
        Optional<GiftCertificate> certificate = certificateService.findById(2L);
        String expected = "certificate2";
        String actual = certificate.get().getName();
        assertEquals(expected, actual);
    }

    @Test
    void create() throws ServiceException {
        GiftCertificate certificate = new GiftCertificate();
        Mockito.when(certificateRepository.create(certificate)).thenReturn(certificate);
        GiftCertificate actual = certificateService.create(certificate);
        Mockito.verify(certificateRepository, Mockito.times(1)).create(certificate);
        assertEquals(certificate, actual);
    }

    @Test
    void deleteById() {
        certificateService.deleteById(777L);
        Mockito.verify(certificateRepository, Mockito.times(1)).deleteById(777L);
    }

    @Test
    void updateFalse() {
        assertThrows(ServiceException.class,
                () -> certificateService.update(0L,new GiftCertificate()));
    }

    @Test
    void update() throws ServiceException {
        GiftCertificate expected = new GiftCertificate();
        Mockito.when(certificateRepository.findById(1L)).thenReturn(Optional.of(expected));
        Mockito.when(certificateRepository.update(1L, expected)).thenReturn(expected);
        GiftCertificate actual = certificateService.update(1L, expected);
        assertEquals(expected, actual);
    }
}
