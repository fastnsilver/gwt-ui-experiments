package name.cphillipson.experimental.gwt.client.module.common.widget.grid;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import name.cphillipson.experimental.gwt.client.module.common.widget.grid.ValidatableInputCell.ValidationData;

public abstract class AbstractValidatableColumn<T> extends Column<T, String> {

    public AbstractValidatableColumn(int inputSize, final AbstractCellTable<T> table) {
        super(new ValidatableInputCell());
        getCell().setInputSize(inputSize);

        setFieldUpdater(new FieldUpdater<T, String>() {
            @Override
            public void update(int index, T dto, String value) {
                final Set<ConstraintViolation<T>> violations = validate(dto);
                final ValidationData viewData = getCell().getViewData(dto);
                if (!violations.isEmpty()) {  // invalid
                    final StringBuffer errorMessage = new StringBuffer();
                    for (final ConstraintViolation<T> constraintViolation : violations) {
                        errorMessage.append(constraintViolation.getMessage());
                    }
                    viewData.setInvalid(true);
                    getCell().setErrorMessage(errorMessage.toString());
                    table.redraw();
                } else {  // valid
                    viewData.setInvalid(false);
                    getCell().setErrorMessage(null);
                    doUpdate(index, dto, value);
                }
            }
        });
    }

    @Override
    public ValidatableInputCell getCell() {
        return (ValidatableInputCell) super.getCell();
    }

    protected abstract void doUpdate(int index, T dto, String value);

    protected Set<ConstraintViolation<T>> validate(T dto) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final Set<ConstraintViolation<T>> violations = validator.validate(dto);
        return violations;
    }
}
