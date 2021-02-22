package ru.netology.patient.service.medical;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.service.alert.SendAlertService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MedicalServiceImplTest {

    @Test
    void testOfDisplayedMessageInCheckBloodPressureMethod() {
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        when(patientInfoFileRepository.getById(Mockito.any()))
                .thenReturn(new PatientInfo("id","Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);
        medicalService.checkBloodPressure("id", new BloodPressure(20, 10));
        verify(sendAlertService).send(Mockito.any());
    }

    @Test
    void testOfDisplayedMessageInCheckTemperatureMethod() {
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        when(patientInfoFileRepository.getById(Mockito.any()))
                .thenReturn(new PatientInfo("id","Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);
        medicalService.checkTemperature("id", new BigDecimal("10"));
        verify(sendAlertService).send(Mockito.any());
    }

    @Test
    void testMessagesAreNotDisplayedIfPatientIsHealthy() {
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        when(patientInfoFileRepository.getById(Mockito.any()))
                .thenReturn(new PatientInfo("id","Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);

        medicalService.checkTemperature("id", new BigDecimal("36.6"));
        verify(sendAlertService, Mockito.times(0)).send(Mockito.any());

        medicalService.checkBloodPressure("id", new BloodPressure(120,80));
        verify(sendAlertService, Mockito.times(0)).send(Mockito.any());
    }
}
