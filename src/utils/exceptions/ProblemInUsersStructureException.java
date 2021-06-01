package utils.exceptions;

/**
 * @author Michał Ziober
 * Wyjątek wyrzucany gdy nastąpił problem w deklarowanej strukturze użytkownika (np różne rozmiary linii)
 */
public class ProblemInUsersStructureException extends Exception {

    @Override
    public String getMessage() {
        ExceptionsDialogs.warningDialog("Warning", "Typed incorrect number of arguments in user`s structure");
        return "Typed incorrect number of arguments in user`s structure";
    }
}
