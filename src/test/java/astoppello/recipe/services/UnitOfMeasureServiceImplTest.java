package astoppello.recipe.services;

import astoppello.recipe.commands.UnitOfMeasureCommand;
import astoppello.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import astoppello.recipe.models.UnitOfMeasure;
import astoppello.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService service;
    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Mock
    UnitOfMeasureRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        converter  = new UnitOfMeasureToUnitOfMeasureCommand();
        service = new UnitOfMeasureServiceImpl(repository,converter);
    }

    @Test
    void listAllUoms() {
        //given
        Set<UnitOfMeasure> set = new HashSet<>();
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(1L);
        set.add(unitOfMeasure);
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId(2L);
        set.add(unitOfMeasure1);
        //when
        when(repository.findAll()).thenReturn(set);
        Set<UnitOfMeasureCommand> commands = service.listAllUoms();

        assertEquals(2, commands.size());
        verify(repository, times(1)).findAll();
    }
}