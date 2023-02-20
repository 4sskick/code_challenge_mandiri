package id.niteroomcreation.archcomponent.util;

import android.util.Log;

import java.util.Arrays;

/**
 * Created by monta on 05/05/21
 * please make sure to use credit when using people code
 **/
public class LogHelper {
    private static final int STACK_TRACE_LEVELS_UP = 5;
    private static final boolean LOGGING_ENABLED = true;

    public static void e(String tag) {
        if (LOGGING_ENABLED) {
            Log.e(tag, getClassNameMethodNameAndLineNumber() + "\n\n");
        }
    }

    public static void e(String tag, Object message) {
        if (LOGGING_ENABLED) {
            Log.e(tag, getClassNameMethodNameAndLineNumber() + ", " + message + "\n");
        }
    }

    public static void e(String tag, Object... message) {
        if (LOGGING_ENABLED) {
            Log.e(tag, getClassNameMethodNameAndLineNumber() + ", " + Arrays.toString(message) + "\n");
        }
    }

    /**
     * Get the current line number. Note, this will only work as called from
     * this class as it has to go a predetermined number of steps up the stack
     * trace. In this case 5.
     *
     * @return int - Current line number.
     */
    private static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[STACK_TRACE_LEVELS_UP].getLineNumber();
    }

    /**
     * Get the current method name. Note, this will only work as called from
     * this class as it has to go a predetermined number of steps up the stack
     * trace. In this case 5.
     *
     * @return String - Current line number.
     */
    private static String getMethodName() {
        return Thread.currentThread().getStackTrace()[STACK_TRACE_LEVELS_UP].getMethodName();
    }

    /**
     * Returns the class name, method name, and line number from the currently
     * executing log call in the form <class_name>.<method_name>()-<line_number>
     *
     * @return String - String representing class name, method name, and line
     * number.
     */
    private static String getClassNameMethodNameAndLineNumber() {
        return "Line " + getLineNumber() + ", " + getMethodName() + "()";
    }

}