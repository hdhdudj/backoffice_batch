package io.spring.main.model.order;

import java.util.List;

import io.spring.main.model.order.entity.IfOrderDetail;
import io.spring.main.model.order.entity.IfOrderMaster;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IfItem {

	private OrderSearchData orderSearchData;
	private IfOrderMaster iom;
	private List<IfOrderDetail> iods;

	public IfItem() {
		super();
		// TODO Auto-generated constructor stub
	}

}
