package io.spring.main.interfaces;

import io.spring.main.model.goods.entity.Itadgs;
import io.spring.main.model.goods.entity.Itasrd;
import io.spring.main.model.goods.entity.Itasrt;
import io.spring.main.model.goods.entity.Ititmm;
import io.spring.main.model.goods.entity.Itvari;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-25T01:11:17+0900",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 14.0.2 (AdoptOpenJDK)"
)
@Component
public class ItasrtMapperImpl implements ItasrtMapper {

    @Override
    public Itasrt to(String assortId, Itadgs ia) {
        if ( assortId == null && ia == null ) {
            return null;
        }

        Itasrt itasrt = new Itasrt();

        if ( ia != null ) {
            itasrt.setAssortId( ia.getAddGoodsId() );
            itasrt.setAssortNm( ia.getAddGoodsNm() );
            itasrt.setAssortModel( ia.getAddGoodsModel() );
            itasrt.setShortageYn( ia.getShortYn() );
            itasrt.setManufactureNm( ia.getMakerNm() );
            itasrt.setRegId( ia.getRegId() );
            itasrt.setUpdId( ia.getUpdId() );
            itasrt.setRegDt( ia.getRegDt() );
            itasrt.setUpdDt( ia.getUpdDt() );
            itasrt.setTaxGb( ia.getTaxGb() );
            itasrt.setBrandId( ia.getBrandId() );
            itasrt.setDeliPrice( ia.getDeliPrice() );
            itasrt.setLocalPrice( ia.getLocalPrice() );
            itasrt.setLocalSale( ia.getLocalSale() );
            itasrt.setStockCnt( ia.getStockCnt() );
        }
        itasrt.setAddGoodsYn( "01" );
        itasrt.setAssortState( "01" );

        return itasrt;
    }

    @Override
    public Itasrt copy(Itasrt it) {
        if ( it == null ) {
            return null;
        }

        Itasrt itasrt = new Itasrt();

        itasrt.setRegId( it.getRegId() );
        itasrt.setUpdId( it.getUpdId() );
        itasrt.setRegDt( it.getRegDt() );
        itasrt.setUpdDt( it.getUpdDt() );
        itasrt.setAssortId( it.getAssortId() );
        itasrt.setAssortNm( it.getAssortNm() );
        itasrt.setAssortModel( it.getAssortModel() );
        itasrt.setMargin( it.getMargin() );
        itasrt.setTaxGb( it.getTaxGb() );
        itasrt.setAssortGb( it.getAssortGb() );
        itasrt.setAssortState( it.getAssortState() );
        itasrt.setAsWidth( it.getAsWidth() );
        itasrt.setAsLength( it.getAsLength() );
        itasrt.setAsHeight( it.getAsHeight() );
        itasrt.setWeight( it.getWeight() );
        itasrt.setOrigin( it.getOrigin() );
        itasrt.setShortageYn( it.getShortageYn() );
        itasrt.setBrandId( it.getBrandId() );
        itasrt.setCategoryId( it.getCategoryId() );
        itasrt.setDispCategoryId( it.getDispCategoryId() );
        itasrt.setSiteGb( it.getSiteGb() );
        itasrt.setOwnerId( it.getOwnerId() );
        itasrt.setManufactureNm( it.getManufactureNm() );
        itasrt.setDeliPrice( it.getDeliPrice() );
        itasrt.setLocalPrice( it.getLocalPrice() );
        itasrt.setLocalDeliFee( it.getLocalDeliFee() );
        itasrt.setLocalSale( it.getLocalSale() );
        itasrt.setAssortColor( it.getAssortColor() );
        itasrt.setSellStaDt( it.getSellStaDt() );
        itasrt.setSellEndDt( it.getSellEndDt() );
        itasrt.setMdRrp( it.getMdRrp() );
        itasrt.setMdTax( it.getMdTax() );
        itasrt.setMdYear( it.getMdYear() );
        itasrt.setMdMargin( it.getMdMargin() );
        itasrt.setMdVatrate( it.getMdVatrate() );
        itasrt.setMdOfflinePrice( it.getMdOfflinePrice() );
        itasrt.setMdOnlinePrice( it.getMdOnlinePrice() );
        itasrt.setMdGoodsVatrate( it.getMdGoodsVatrate() );
        itasrt.setBuyWhere( it.getBuyWhere() );
        itasrt.setBuyTax( it.getBuyTax() );
        itasrt.setBuySupplyDiscount( it.getBuySupplyDiscount() );
        itasrt.setBuyRrpIncrement( it.getBuyRrpIncrement() );
        itasrt.setBuyExchangeRate( it.getBuyExchangeRate() );
        itasrt.setMdDiscountRate( it.getMdDiscountRate() );
        itasrt.setOptionGbName( it.getOptionGbName() );
        itasrt.setOptionUseYn( it.getOptionUseYn() );
        itasrt.setStorageId( it.getStorageId() );
        itasrt.setAddGoodsYn( it.getAddGoodsYn() );
        itasrt.setAddOptionNm( it.getAddOptionNm() );
        itasrt.setAddImageUrl( it.getAddImageUrl() );
        itasrt.setStockCnt( it.getStockCnt() );
        List<Itvari> list = it.getItvariList();
        if ( list != null ) {
            itasrt.setItvariList( new ArrayList<Itvari>( list ) );
        }
        List<Ititmm> list1 = it.getItitmmList();
        if ( list1 != null ) {
            itasrt.setItitmmList( new ArrayList<Ititmm>( list1 ) );
        }
        List<Itasrd> list2 = it.getItasrdList();
        if ( list2 != null ) {
            itasrt.setItasrdList( new ArrayList<Itasrd>( list2 ) );
        }
        itasrt.setItbrnd( it.getItbrnd() );
        itasrt.setItcatg( it.getItcatg() );

        return itasrt;
    }
}
