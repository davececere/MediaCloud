package com.cecere.cling.converter;

import org.teleal.cling.support.model.item.Item;

import com.cecere.converter.Converter;
import com.cecere.domain.dlna.Content;

public class ClingItemToContentConverter implements Converter<Item, Content> {

	@Override
	public Content convertTo(Item from) {
		return new Content();
	}

	@Override
	public Item convertFrom(Content to) {
		throw new UnsupportedOperationException("not yet supported");
	}

}
