package com.caej.batch.reader;


import com.caej.batch.model.BufAccountConfirm;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * Builds a Product from a row in the CSV file (as a set of fields)
 */
public class ProductFieldSetMapper implements FieldSetMapper<BufAccountConfirm>
{
    @Override
    public BufAccountConfirm mapFieldSet(FieldSet fieldSet) throws BindException {
        BufAccountConfirm product = new BufAccountConfirm();
        product.setId( fieldSet.readString( "id" ) );
//        product.setName( fieldSet.readString( "name" ) );
//        product.setDescription( fieldSet.readString( "description" ) );
//        product.setQuantity( fieldSet.readInt( "quantity" ) );
        return product;
    }
}
