package com.grouptwo.soccer.transfers.converters;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.grouptwo.soccer.transfers.lib.requests.TransferRequest;
import com.grouptwo.soccer.transfers.lib.responses.TransferResponse;
import com.grouptwo.soccer.transfers.models.Transfer;

@Component
public class TransferConverter {

	public TransferResponse fromEntityToResponse(Transfer entity) {
		TransferResponse tr = new TransferResponse();
		BeanUtils.copyProperties(entity, tr);
		return tr;
	}

	public Transfer fromResponseToEntity(TransferResponse tr) {
		Transfer entity = new Transfer();
		BeanUtils.copyProperties(tr, entity);
		return entity;
	}

	public Transfer fromRequestToEntity(TransferRequest tr) {
		Transfer entity = new Transfer();
		BeanUtils.copyProperties(tr, entity);
		return entity;
	}
}
