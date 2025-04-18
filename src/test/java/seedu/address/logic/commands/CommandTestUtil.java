package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOALS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_HISTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ONETIMESCHEDULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURRING_SCHEDULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "88888888";
    public static final String VALID_PHONE_BOB = "99999999";
    public static final String VALID_RECURRING_SCHEDULE_AMY = "Mon 1400 1600";
    public static final String VALID_RECURRING_SCHEDULE_BOB = "Wed 1500 1700";
    public static final String VALID_GOALS_AMY = "Train arms";
    public static final String VALID_GOALS_BOB = "Train back";
    public static final String VALID_MEDICAL_HISTORY_AMY = "Twisted right ankle";
    public static final String VALID_MEDICAL_HISTORY_BOB = "Upper back injury";
    public static final String VALID_LOCATION_AMY = "Block 312, Amy Street 1";
    public static final String VALID_LOCATION_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_ONETIMESCHEDULE_AMY = "2/2 1000 1200";
    public static final String VALID_ONETIMESCHEDULE_BOB = "3/6 1000 1200";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String RECURRING_SCHEDULE_DESC_AMY = " " + PREFIX_RECURRING_SCHEDULE
            + VALID_RECURRING_SCHEDULE_AMY;
    public static final String RECURRING_SCHEDULE_DESC_BOB = " " + PREFIX_RECURRING_SCHEDULE
            + VALID_RECURRING_SCHEDULE_BOB;
    public static final String GOALS_DESC_AMY = " " + PREFIX_GOALS + VALID_GOALS_AMY;
    public static final String GOALS_DESC_BOB = " " + PREFIX_GOALS + VALID_GOALS_BOB;
    public static final String MEDICAL_HISTORY_DESC_AMY = " " + PREFIX_MEDICAL_HISTORY + VALID_MEDICAL_HISTORY_AMY;
    public static final String MEDICAL_HISTORY_DESC_BOB = " " + PREFIX_MEDICAL_HISTORY + VALID_MEDICAL_HISTORY_BOB;
    public static final String LOCATION_DESC_AMY = " " + PREFIX_LOCATION + VALID_LOCATION_AMY;
    public static final String LOCATION_DESC_BOB = " " + PREFIX_LOCATION + VALID_LOCATION_BOB;
    public static final String ONETIMESCHEDULE_DESC_AMY = " " + PREFIX_ONETIMESCHEDULE + VALID_ONETIMESCHEDULE_AMY;
    public static final String ONETIMESCHEDULE_DESC_BOB = " " + PREFIX_ONETIMESCHEDULE + VALID_ONETIMESCHEDULE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Jamesé"; // 'é' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_RECURRING_SCHEDULE_DESC = " " + PREFIX_RECURRING_SCHEDULE
            + "yay"; // yay is not a valid day
    public static final String INVALID_GOALS_DESC = " " + PREFIX_GOALS; // empty string not allowed for goals
    public static final String INVALID_MEDICAL_HISTORY_DESC = " " + PREFIX_MEDICAL_HISTORY + "é©"; //non-ASCII character
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION; // empty string not allowed for addresses
    // empty string not allowed for one time date
    public static final String INVALID_ONETIMESCHEDULE_DESC = " " + PREFIX_ONETIMESCHEDULE + "abc";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubbyé"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withRecurringSchedules(VALID_RECURRING_SCHEDULE_AMY).withGoals(VALID_GOALS_AMY)
                .withMedicalHistory(VALID_MEDICAL_HISTORY_AMY).withLocation(VALID_LOCATION_AMY)
                .withOneTimeSchedules(VALID_ONETIMESCHEDULE_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withRecurringSchedules(VALID_RECURRING_SCHEDULE_BOB).withGoals(VALID_GOALS_BOB)
                .withMedicalHistory(VALID_MEDICAL_HISTORY_BOB).withLocation(VALID_LOCATION_BOB)
                .withOneTimeSchedules(VALID_ONETIMESCHEDULE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the command execution was successful (including for AddCommand and EditCommand with potential conflicts) <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccessWithPotentialConflicts(Command command, Model actualModel,
                                                                  Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            // We don't check the actual message content as it may contain conflict messages
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail: ", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
