package com.company.sample.web.customer;

import com.company.sample.entity.Customer;
import com.company.sample.entity.CustomerGrade;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.chile.core.model.MetaPropertyPath;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.data.GroupInfo;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class CustomerBrowse extends AbstractLookup {
    @Inject
    private GroupTable<Customer> customersTable;
    @Inject
    private GroupDatasource<Customer, UUID> customersDs;

    @Override
    public void init(Map<String, Object> params) {
        customersTable.setStyleProvider(new GroupTable.GroupStyleProvider<Customer>() {
            @Nullable
            @Override
            public String getStyleName(GroupInfo info) {
                MetaPropertyPath gradePropertyPath = customersDs.getMetaClass().getPropertyPath("grade");

                if (gradePropertyPath != null && gradePropertyPath.equals(info.getProperty())) {
                    CustomerGrade grade = (CustomerGrade) info.getValue();
                    return getGradeStyle(grade);
                }
                return null;
            }

            @Override
            public String getStyleName(Customer entity, @Nullable String property) {
                if (property == null) {
                    return getGradeStyle(entity.getGrade());
                }
                return null;
            }
        });
    }

    private String getGradeStyle(CustomerGrade grade) {
        switch (grade) {
            case PREMIUM:
                return "premium-grade";
            case HIGH:
                return "high-grade";
            case STANDARD:
                return "standard-grade";
            default:
                return null;
        }
    }
}