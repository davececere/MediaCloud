package com.cecere.dlna.cling.converter;

import org.teleal.cling.support.model.item.Item;

import com.cecere.converter.Converter;
import com.cecere.dlna.domain.Content;

public class ClingItemToContentConverter implements Converter<Item, Content> {

	@Override
	public Content convertTo(Item from) {
		Content ret = new Content();
		ret.setId(from.getId());
		ret.setTitle(from.getTitle());
		ret.setMediaUri(from.getResources().get(0).getValue());
		return ret;
	}

	@Override
	public Item convertFrom(Content to) {
		throw new UnsupportedOperationException("not yet supported");
	}

}
