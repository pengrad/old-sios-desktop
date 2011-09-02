package model.viewmodel;

import model.util.Helper;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.text.ParseException;

/**
 * User: parshin
 * Date: 21.12.10
 * Time: 15:56
 */

public class SpinnerEditor extends JSpinner.DefaultEditor {

    public SpinnerEditor(JSpinner spinner) {
        super(spinner);
        JFormattedTextField tf = getTextField();
        tf.setEditable(true);
        tf.setFormatterFactory(new JFormattedTextField.AbstractFormatterFactory() {
            @Override
            public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
                return new TimeEditorFormatter();
            }
        });
    }

    public class TimeEditorFormatter extends DateFormatter {

        @Override
        public Object stringToValue(String text) throws ParseException {
            try {
                int index = text.indexOf(':');
                String h = text.substring(0, index);
                String m = text.substring(index+1, index+3);
                int hh = Integer.valueOf(h);
                int mm = Integer.valueOf(m);
                return hh * 60 + mm;
            } catch (Exception e) {
                throw new ParseException(text, 0);
            }
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            try {
                int val = Integer.parseInt(value.toString());
                return Helper.getMinutesToString(val);
            } catch (Exception e) {
                throw new ParseException(value.toString(), 0);
            }
        }
    }
}
