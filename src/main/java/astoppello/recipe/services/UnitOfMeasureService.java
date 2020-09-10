package astoppello.recipe.services;

import astoppello.recipe.commands.UnitOfMeasureCommand;

import java.util.Set;

/**
 * Created by @author stopp on 09/08/2020
 */
public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUoms();
}
