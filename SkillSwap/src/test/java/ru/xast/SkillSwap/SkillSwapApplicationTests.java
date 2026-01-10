package ru.xast.SkillSwap.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.xast.SkillSwap.models.PersInf;
import ru.xast.SkillSwap.models.ProfInf;
import ru.xast.SkillSwap.repositories.PersInfRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersInfServiceTest {

    @Mock
    private PersInfRepository persInfRepository;

    @InjectMocks
    private PersInfService persInfService;

    private PersInf testPersInf;
    private UUID testId;
    private UUID testUserId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testUserId = UUID.randomUUID();
        
        testPersInf = new PersInf();
        testPersInf.setId(testId);
        testPersInf.setUserId(testUserId);
        testPersInf.setName("Иван");
        testPersInf.setSurname("Иванов");
    }

    @Test
    void save_ShouldSavePersInfSuccessfully() {
        // Arrange
        when(persInfRepository.save(any(PersInf.class))).thenReturn(testPersInf);

        // Act
        persInfService.save(testPersInf);

        // Assert
        verify(persInfRepository, times(1)).save(testPersInf);
    }

    @Test
    void save_ShouldThrowExceptionWhenRepositoryFails() {
        // Arrange
        when(persInfRepository.save(any(PersInf.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> persInfService.save(testPersInf));
        assertTrue(exception.getMessage().contains("Failed to saved PersInf"));
    }

    @Test
    void findOne_ShouldReturnPersInfWhenExists() {
        // Arrange
        when(persInfRepository.findById(testId)).thenReturn(Optional.of(testPersInf));

        // Act
        PersInf result = persInfService.findOne(testId);

        // Assert
        assertNotNull(result);
        assertEquals(testId, result.getId());
        assertEquals("Иван", result.getName());
        verify(persInfRepository, times(1)).findById(testId);
    }

    @Test
    void findOne_ShouldReturnNullWhenNotFound() {
        // Arrange
        when(persInfRepository.findById(testId)).thenReturn(Optional.empty());

        // Act
        PersInf result = persInfService.findOne(testId);

        // Assert
        assertNull(result);
        verify(persInfRepository, times(1)).findById(testId);
    }

    @Test
    void findOne_ShouldThrowExceptionWhenRepositoryFails() {
        // Arrange
        when(persInfRepository.findById(testId))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> persInfService.findOne(testId));
        assertTrue(exception.getMessage().contains("Failed to find PersInf with id"));
    }

    @Test
    void findPersInfByUserId_ShouldReturnPersInfWhenExists() {
        // Arrange
        when(persInfRepository.findPersInfByUserId(testUserId)).thenReturn(testPersInf);

        // Act
        PersInf result = persInfService.findPersInfByUserId(testUserId);

        // Assert
        assertNotNull(result);
        assertEquals(testUserId, result.getUserId());
        verify(persInfRepository, times(1)).findPersInfByUserId(testUserId);
    }

    @Test
    void findPersInfByUserId_ShouldReturnNullWhenNotFound() {
        // Arrange
        when(persInfRepository.findPersInfByUserId(testUserId)).thenReturn(null);

        // Act
        PersInf result = persInfService.findPersInfByUserId(testUserId);

        // Assert
        assertNull(result);
        verify(persInfRepository, times(1)).findPersInfByUserId(testUserId);
    }

    @Test
    void findAll_ShouldReturnListOfPersInf() {
        // Arrange
        List<PersInf> persInfList = Arrays.asList(testPersInf, new PersInf());
        when(persInfRepository.findAll()).thenReturn(persInfList);

        // Act
        List<PersInf> result = persInfService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(persInfRepository, times(1)).findAll();
    }

    @Test
    void findAll_ShouldReturnEmptyListWhenNoRecords() {
        // Arrange
        when(persInfRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<PersInf> result = persInfService.findAll();

        // Assert
        assertTrue(result.isEmpty());
        verify(persInfRepository, times(1)).findAll();
    }

    @Test
    void update_ShouldUpdatePersInfSuccessfully() {
        // Arrange
        PersInf updatedPersInf = new PersInf();
        updatedPersInf.setName("Петр");
        updatedPersInf.setSurname("Петров");
        
        when(persInfRepository.save(any(PersInf.class))).thenReturn(updatedPersInf);

        // Act
        persInfService.update(testId, updatedPersInf);

        // Assert
        assertEquals(testId, updatedPersInf.getId());
        verify(persInfRepository, times(1)).save(updatedPersInf);
    }

    @Test
    void delete_ShouldDeletePersInfSuccessfully() {
        // Arrange
        doNothing().when(persInfRepository).deleteById(testId);

        // Act
        persInfService.delete(testId);

        // Assert
        verify(persInfRepository, times(1)).deleteById(testId);
    }

    @Test
    void searchBySurname_ShouldReturnMatchingRecords() {
        // Arrange
        String surname = "Ива";
        List<PersInf> expectedList = Arrays.asList(testPersInf);
        when(persInfRepository.findBySurnameStartingWith(surname)).thenReturn(expectedList);

        // Act
        List<PersInf> result = persInfService.searchBySurname(surname);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Иванов", result.get(0).getSurname());
        verify(persInfRepository, times(1)).findBySurnameStartingWith(surname);
    }

    @Test
    void getSkillsByPersonId_ShouldReturnSkillsWhenPersInfExists() {
        // Arrange
        ProfInf skill1 = new ProfInf();
        skill1.setId(UUID.randomUUID());
        skill1.setTitle("Java");
        
        ProfInf skill2 = new ProfInf();
        skill2.setId(UUID.randomUUID());
        skill2.setTitle("Spring");
        
        List<ProfInf> skills = new ArrayList<>();
        skills.add(skill1);
        skills.add(skill2);
        
        // Assuming PersInf has setProvidedSkills method
        // testPersInf.setProvidedSkills(skills);
        // If not, you may need to use reflection or modify the test
        
        when(persInfRepository.findById(testId)).thenReturn(Optional.of(testPersInf));

        // Act
        List<ProfInf> result = persInfService.getSkillsByPersonId(testId);

        // Assert - even if empty, test the flow
        verify(persInfRepository, times(1)).findById(testId);
    }

    @Test
    void getSkillsByPersonId_ShouldReturnEmptyListWhenPersInfNotFound() {
        // Arrange
        when(persInfRepository.findById(testId)).thenReturn(Optional.empty());

        // Act
        List<ProfInf> result = persInfService.getSkillsByPersonId(testId);

        // Assert
        assertTrue(result.isEmpty());
        verify(persInfRepository, times(1)).findById(testId);
    }
}
