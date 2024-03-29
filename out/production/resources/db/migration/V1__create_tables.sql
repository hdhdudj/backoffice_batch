create table if not exists BATCH_JOB_EXECUTION_SEQ
(
    ID bigint not null,
    UNIQUE_KEY char not null,
    constraint UNIQUE_KEY_UN
    unique (UNIQUE_KEY)
    );

create table if not exists BATCH_JOB_INSTANCE
(
    JOB_INSTANCE_ID bigint not null
    primary key,
    VERSION bigint null,
    JOB_NAME varchar(100) not null,
    JOB_KEY varchar(32) not null,
    constraint JOB_INST_UN
    unique (JOB_NAME, JOB_KEY)
    );

create table if not exists BATCH_JOB_EXECUTION
(
    JOB_EXECUTION_ID bigint not null
    primary key,
    VERSION bigint null,
    JOB_INSTANCE_ID bigint not null,
    CREATE_TIME datetime not null,
    START_TIME datetime null,
    END_TIME datetime null,
    STATUS varchar(10) null,
    EXIT_CODE varchar(2500) null,
    EXIT_MESSAGE varchar(2500) null,
    LAST_UPDATED datetime null,
    JOB_CONFIGURATION_LOCATION varchar(2500) null,
    constraint JOB_INST_EXEC_FK
    foreign key (JOB_INSTANCE_ID) references BATCH_JOB_INSTANCE (JOB_INSTANCE_ID)
    );

create table if not exists BATCH_JOB_EXECUTION_CONTEXT
(
    JOB_EXECUTION_ID bigint not null
    primary key,
    SHORT_CONTEXT varchar(2500) not null,
    SERIALIZED_CONTEXT text null,
    constraint JOB_EXEC_CTX_FK
    foreign key (JOB_EXECUTION_ID) references BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
    );

create table if not exists BATCH_JOB_EXECUTION_PARAMS
(
    JOB_EXECUTION_ID bigint not null,
    TYPE_CD varchar(6) not null,
    KEY_NAME varchar(100) not null,
    STRING_VAL varchar(250) null,
    DATE_VAL datetime null,
    LONG_VAL bigint null,
    DOUBLE_VAL double null,
    IDENTIFYING char not null,
    constraint JOB_EXEC_PARAMS_FK
    foreign key (JOB_EXECUTION_ID) references BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
    );

create table if not exists BATCH_JOB_SEQ
(
    ID bigint not null,
    UNIQUE_KEY char not null,
    constraint UNIQUE_KEY_UN
    unique (UNIQUE_KEY)
    );

create table if not exists BATCH_STEP_EXECUTION
(
    STEP_EXECUTION_ID bigint not null
    primary key,
    VERSION bigint not null,
    STEP_NAME varchar(100) not null,
    JOB_EXECUTION_ID bigint not null,
    START_TIME datetime not null,
    END_TIME datetime null,
    STATUS varchar(10) null,
    COMMIT_COUNT bigint null,
    READ_COUNT bigint null,
    FILTER_COUNT bigint null,
    WRITE_COUNT bigint null,
    READ_SKIP_COUNT bigint null,
    WRITE_SKIP_COUNT bigint null,
    PROCESS_SKIP_COUNT bigint null,
    ROLLBACK_COUNT bigint null,
    EXIT_CODE varchar(2500) null,
    EXIT_MESSAGE varchar(2500) null,
    LAST_UPDATED datetime null,
    constraint JOB_EXEC_STEP_FK
    foreign key (JOB_EXECUTION_ID) references BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
    );

create table if not exists BATCH_STEP_EXECUTION_CONTEXT
(
    STEP_EXECUTION_ID bigint not null
    primary key,
    SHORT_CONTEXT varchar(2500) not null,
    SERIALIZED_CONTEXT text null,
    constraint STEP_EXEC_CTX_FK
    foreign key (STEP_EXECUTION_ID) references BATCH_STEP_EXECUTION (STEP_EXECUTION_ID)
    );

create table if not exists BATCH_STEP_EXECUTION_SEQ
(
    ID bigint not null,
    UNIQUE_KEY char not null,
    constraint UNIQUE_KEY_UN
    unique (UNIQUE_KEY)
    );

create table if not exists EMP_ATTEND
(
    ATTEND_YMD varchar(8) collate utf8_bin not null,
    EMP_NO varchar(9) collate utf8_bin not null
    );

create table if not exists article_favorites
(
    article_id varchar(255) not null,
    user_id varchar(255) not null,
    primary key (article_id, user_id)
    );

create table if not exists article_tags
(
    article_id varchar(255) not null,
    tag_id varchar(255) not null
    );

create table if not exists articles
(
    id varchar(255) not null
    primary key,
    user_id varchar(255) null,
    slug varchar(255) null,
    title varchar(255) null,
    description text null,
    body text null,
    created_at timestamp not null,
    updated_at timestamp default CURRENT_TIMESTAMP not null,
    constraint slug
    unique (slug)
    );

create table if not exists cmstgm
(
    storage_id varchar(6) not null comment '창고코드'
    primary key,
    storage_nm varchar(100) null comment '창고명',
    storage_gb varchar(2) null comment '창고구분',
    storage_own_cd varchar(2) null comment '소유구분',
    storage_type varchar(2) null comment '창고타입',
    zip_cd varchar(6) null comment '우편번호',
    addr1 varchar(255) null comment '주소1',
    addr2 varchar(255) null comment '주소2',
    store_tel varchar(100) null comment '창고전화',
    mobile_tel varchar(100) null comment '이동전화',
    vendor_id varchar(9) null comment '거래처',
    up_storage_id varchar(6) not null comment '상위창고코드',
    user_nm varchar(100) not null comment '창고담당자명',
    area_gb varchar(2) not null comment '지역',
    del_yn varchar(2) null comment '삭제여부',
    reg_id int(10) null comment '작성자',
    reg_dt datetime default CURRENT_TIMESTAMP null comment '작성일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime default CURRENT_TIMESTAMP null comment '수정일'
    );

create table if not exists cmvdmr
(
    vendor_id varchar(9) not null comment '거래처코드'
    primary key,
    vd_nm varchar(100) null comment '거래처명',
    vd_enm varchar(100) null comment '거래처영문명',
    vendor_type varchar(2) null comment '거래처타입',
    terms varchar(100) null comment 'terms',
    delivery varchar(100) null comment 'delivery',
    payment varchar(100) null comment 'payment',
    carrier varchar(100) null comment 'carrier',
    reg_id int(10) null comment '작성자',
    reg_dt datetime null comment '작성일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일'
    );

create table if not exists comments
(
    id varchar(255) not null
    primary key,
    body text null,
    article_id varchar(255) null,
    user_id varchar(255) null,
    created_at timestamp not null,
    updated_at timestamp default CURRENT_TIMESTAMP not null
    );

create table if not exists flyway_schema_history
(
    installed_rank int not null
    primary key,
    version varchar(50) null,
    description varchar(200) not null,
    type varchar(20) not null,
    script varchar(1000) not null,
    checksum int null,
    installed_by varchar(100) not null,
    installed_on timestamp default CURRENT_TIMESTAMP not null,
    execution_time int not null,
    success tinyint(1) not null
    );

create index flyway_schema_history_s_idx
	on flyway_schema_history (success);

create table if not exists follows
(
    user_id varchar(255) not null,
    follow_id varchar(255) not null
    );

create table if not exists if_brand
(
    channel_gb varchar(2) charset utf8mb4 not null comment '채널구분',
    channel_brand_id varchar(25) charset utf8mb4 not null comment '채널 브랜드 코드',
    channel_brand_nm varchar(255) charset utf8mb4 not null comment '채널 브랜드 명',
    brand_id varchar(9) charset utf8mb4 null comment '브랜드코드',
    brand_nm varchar(255) charset utf8mb4 null comment '브랜드명',
    reg_dt datetime null comment '등록일',
    upd_dt datetime null comment '수정일',
    reg_id int(10) null comment '작성자',
    upd_id int(10) null comment '수정자',
    primary key (channel_gb, channel_brand_id)
    )
    comment '브랜드 매핑';

create table if not exists if_category
(
    channel_gb varchar(2) charset utf8mb4 not null comment '채널구분',
    channel_category_id varchar(25) charset utf8mb4 not null comment '채널 카테고리 코드',
    channel_category_nm varchar(255) charset utf8mb4 not null comment '채널 카테고리 명',
    category_id varchar(9) charset utf8mb4 null comment '카테고리코드',
    category_nm varchar(255) charset utf8mb4 null comment '카테고리명',
    reg_dt datetime null comment '등록일',
    upd_dt datetime null comment '수정일',
    reg_id int(10) null comment '작성자',
    upd_id int(10) null comment '수정자',
    primary key (channel_gb, channel_category_id)
    )
    comment '카테고리 매핑';

create table if not exists if_goods_add_goods
(
    channel_gb varchar(2) not null comment '채널구분',
    goods_no int(25) not null comment '연관상품번호',
    add_goods_no varchar(25) not null comment '추가상품번호',
    add_goods_id varchar(25) null comment 'itadgs 상품번호',
    assort_id varchar(25) null comment '백오피스 상품번호',
    scm_no varchar(25) null comment '공급사코드',
    title varchar(255) null comment '추가상품 표시명',
    goods_nm varchar(255) null comment '추가상품명',
    brand_cd varchar(25) null comment '브랜드코드',
    option_nm varchar(255) null comment '추가상품 옵션명',
    maker_nm varchar(255) null comment '제조사명',
    goods_price decimal(12,2) null comment '추가상품금액',
    stock_cnt int(10) null comment '추가상품 재고',
    view_fl varchar(2) null comment '노출여부',
    sold_out_fl varchar(2) null comment '품절여부',
    reg_dt datetime null comment '등록일',
    mod_dt datetime null comment '수정일',
    reg_id int(10) null comment '작성자',
    upd_id int(10) null comment '수정자',
    upload_status varchar(2) not null comment '업로드상태',
    primary key (channel_gb, goods_no, add_goods_no)
    )
    comment '상품 추가상품 인터페이스';

create table if not exists if_goods_master
(
    channel_gb varchar(2) not null comment '채널구분',
    goods_no varchar(25) not null comment '고도몰 일련번호',
    goods_cd varchar(25) null comment '백오피스 상품번호',
    goods_nm varchar(255) null comment '상품명',
    goods_nm_detail varchar(255) null comment '상세 상품명',
    goods_display_fl varchar(2) null comment 'PC 쇼핑몰 노출상태',
    goods_sell_fl varchar(2) null comment 'PC 쇼핑몰 판매상태',
    cate_cd varchar(9) null comment '대표카테고리',
    goods_color varchar(255) null comment '상품 대표색상',
    commission decimal(12,2) null comment '수수료',
    brand_cd varchar(9) null comment '브랜드코드',
    maker_nm varchar(255) null comment '제조사',
    origin_nm varchar(255) null comment '원산지',
    goods_model_no varchar(255) null comment '모델번호',
    only_adult_fl varchar(9) null comment '성인인증 사용여부 코드',
    tax_free_fl varchar(2) null comment '과세/면세 코드',
    stock_fl varchar(2) null comment '판매재고 코드',
    sold_out_fl varchar(2) null comment '품절상태 코드',
    sales_start_ymd datetime null comment '판매시작일',
    sales_end_ymd datetime null comment '판매종료일',
    goods_price decimal(12,2) null comment '판매가',
    fixed_price decimal(12,2) null comment '정가',
    cost_price decimal(12,2) null comment '매입가',
    option_name varchar(255) null comment '옵션명',
    option_fl varchar(2) null comment '옵션사용여부',
    option_memo varchar(255) null comment '옵션 메모',
    short_description varchar(255) null comment '짧은 설명',
    goods_description mediumtext null comment '긴 설명',
    goods_weight decimal(12,2) null comment '상품 무게',
    width decimal(12,2) null comment '상품 너비',
    height decimal(12,2) null comment '상품 높이',
    depth decimal(12,2) null comment '상품 깊이',
    md_rrp decimal(12,2) null comment '튜닝 : rrp',
    md_tax varchar(10) null comment '튜닝 : 자료',
    md_year varchar(20) null comment '튜닝 : 자료연도',
    md_margin decimal(12,2) null comment '튜닝 : 추가 마진',
    md_vatrate decimal(12,2) null comment '튜닝 : 부가세율',
    md_offline_price decimal(12,2) null comment '튜닝 : 정가(오프라인)',
    md_online_price decimal(12,2) null comment '튜닝 : 판매가(온라인)',
    md_goods_vatrate decimal(12,2) null comment '튜닝 : 상품 마진율',
    md_discount_rate decimal(12,2) null comment '튜닝 : 할인율',
    buy_where varchar(255) null comment '튜닝 : 구매처',
    buy_supply_discount decimal(12,2) null comment '튜닝 : 공급 할인율',
    buy_rrp_increment decimal(12,2) null comment '튜닝 : rrp 인상률',
    buy_exchange_rate decimal(12,2) null comment '튜닝 : 적용환율',
    size_type varchar(10) null comment '튜닝 : 사이즈타입',
    assort_id varchar(9) null comment '백오피스 일련번호',
    reg_dt datetime null comment '등록일',
    mod_dt datetime null comment '수정일',
    reg_id int(10) null comment '작성자',
    upd_id int(10) null comment '수정자',
    upload_status varchar(2) not null comment '업로드상태',
    primary key (channel_gb, goods_no)
    )
    comment '상품 마스터인터페이스';

create table if not exists if_goods_option
(
    channel_gb varchar(2) charset utf8mb4 not null comment '채널구분',
    goods_no varchar(25) charset utf8mb4 not null comment '고도몰 일련번호',
    sno varchar(25) charset utf8mb4 not null comment '고도몰 옵션 고유번호',
    option_no varchar(4) charset utf8mb4 null comment '고도몰 옵션번호',
    option_name varchar(255) charset utf8mb4 null comment '옵션명 모음',
    option_value1 varchar(255) charset utf8mb4 null comment '옵션명1',
    option_value2 varchar(255) charset utf8mb4 null comment '옵션명2',
    option_value3 varchar(255) charset utf8mb4 null comment '옵션명3',
    option_value4 varchar(255) charset utf8mb4 null comment '옵션명4',
    option_value5 varchar(255) charset utf8mb4 null comment '옵션명5',
    option_price decimal(12,2) null comment '옵션가',
    option_view_fl varchar(2) charset utf8mb4 null comment '옵션노출여부',
    option_code varchar(25) charset utf8mb4 null comment '옵션코드',
    sold_out_fl varchar(2) charset utf8mb4 null comment '품절상태 코드',
    min_order_cnt int(5) null comment '최소 구매수량',
    max_order_cnt int(5) null comment '최대 구매수량',
    assort_id varchar(25) charset utf8mb4 null comment 'ititmm 제품번호',
    item_id varchar(5) charset utf8mb4 null comment 'ititmm 아이템번호',
    reg_dt datetime null comment '등록일',
    mod_dt datetime null comment '수정일',
    reg_id int(10) null comment '작성자',
    upd_id int(10) null comment '수정자',
    upload_status varchar(2) charset utf8mb4 not null comment '업로드상태',
    primary key (channel_gb, goods_no, sno)
    )
    comment '상품 옵션 인터페이스';

create table if not exists if_goods_text_option
(
    channel_gb varchar(2) charset utf8mb4 not null comment '채널구분',
    goods_no varchar(10) charset utf8mb4 not null comment '고도몰 상품번호',
    assort_id varchar(9) charset utf8mb4 null comment '백오피스 일련번호',
    option_text_id varchar(4) charset utf8mb4 null comment '백오피스 text id',
    option_name varchar(255) charset utf8mb4 null comment '옵션명',
    must_fl varchar(2) charset utf8mb4 null comment '필수여부',
    add_price decimal(12,2) null comment '추가금액',
    input_limit int(5) null comment '입력제한 글자수',
    reg_dt datetime null comment '등록일',
    mod_dt datetime null comment '수정일',
    reg_id int(10) null comment '작성자',
    upd_id int(10) null comment '수정자',
    upload_status varchar(2) charset utf8mb4 not null comment '업로드상태',
    primary key (channel_gb, goods_no)
    )
    comment '상품 옵션 인터페이스';

create table if not exists if_order_detail
(
    if_no varchar(17) not null,
    if_no_seq varchar(4) not null,
    channel_order_no varchar(20) not null,
    channel_order_seq varchar(20) not null,
    channel_order_status varchar(2) not null,
    channel_goods_type varchar(20) not null,
    channel_goods_no varchar(20) not null,
    channel_options_no varchar(20) not null,
    channel_parent_goods_no varchar(20) null,
    channel_goods_nm varchar(255) not null,
    channel_option_info varchar(255) not null,
    goods_cnt int(10) null,
    goods_price decimal(12,2) null,
    goods_dc_price decimal(12,2) null,
    member_dc_price decimal(12,2) null,
    coupon_dc_price decimal(12,2) null,
    admin_dc_price decimal(12,2) null,
    etc_dc_price decimal(12,2) null,
    delivery_method_gb varchar(20) null,
    delivery_info text null,
    order_id varchar(17) null,
    order_seq varchar(4) null,
    reg_id int unsigned not null,
    reg_dt datetime default CURRENT_TIMESTAMP null,
    upd_id int unsigned not null,
    upd_dt datetime default CURRENT_TIMESTAMP null,
    primary key (if_no, if_no_seq)
    )
    comment '인터페이스_주문디테일';

create table if not exists itadgs
(
    add_goods_id varchar(9) not null comment '추가상품코드'
    primary key,
    add_goods_nm varchar(255) null comment '추가상품명',
    tax_gb varchar(2) null comment '부가세구분',
    add_goods_model varchar(255) null comment '추가상품모델',
    option_nm varchar(255) null comment '추가상품옵션명',
    local_price decimal(12,2) null comment '정가',
    local_sale decimal(12,2) null comment '판매가',
    deli_price decimal(12,2) null comment '원가',
    image_url varchar(255) null comment '이미지 주소',
    add_goods_state varchar(2) null comment '추가상품상태',
    short_yn varchar(2) null comment '추가상품 품절여부',
    brand_id varchar(9) null comment '브랜드코드',
    maker_nm varchar(255) null comment '제조사명',
    stock_cnt int(10) null comment '추가상품 재고',
    reg_id int(10) null comment '작성자',
    reg_dt datetime null comment '작성일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일'
    )
    comment '추가상품';

create table if not exists itaimg
(
    image_seq int auto_increment comment '일련번호'
    primary key,
    image_gb varchar(2) null comment '이미지구분',
    image_name varchar(200) null comment '이미지명',
    image_original_name varchar(200) null comment '원이미지명',
    image_path varchar(255) null comment '이미지경로',
    image_status varchar(2) null comment '상태',
    image_size int null comment '파일사이즈',
    image_type varchar(255) null comment '파일타입',
    assort_id varchar(9) null comment '상품코드',
    reg_id int(10) null comment '등록자',
    reg_dt datetime null comment '등록일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일'
    );

create table if not exists itasrd
(
    assort_id varchar(9) not null comment '상품코드',
    seq varchar(4) not null comment '순번',
    ord_det_cd varchar(2) null comment '구분',
    memo mediumtext null comment '내용',
    del_yn varchar(2) null comment '삭제여부',
    reg_id int(10) null comment '작성자',
    reg_dt datetime null comment '작성일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일',
    text_html_gb varchar(2) null comment 'html 구분',
    memo2 text null comment '내용2',
    primary key (assort_id, seq)
    );

create table if not exists itasrn
(
    seq int auto_increment comment 'seq'
    primary key,
    history_gb varchar(2) null comment '히스토리구분',
    vendor_id varchar(6) null comment '거래처',
    assort_id varchar(9) null comment '상품코드',
    eff_end_dt datetime null comment '종료일',
    eff_sta_dt datetime null comment '시작일',
    add_deli_gb varchar(2) null comment '추가배송구분',
    bonus_reserve decimal(12,2) null comment '추가보상금',
    call_dis_limit decimal(12,2) null comment '?',
    card_fee decimal(12,2) null comment '수수료율',
    coupon_yn varchar(2) null comment '쿠폰사용여부',
    delay_reward_yn varchar(2) null comment '지연보상여부',
    deli_charge decimal(12,2) null comment '배송비',
    deli_interval decimal(12,2) null comment '배송기간',
    deli_mth varchar(2) null comment '배송방법',
    deli_price decimal(12,2) null comment '원가',
    dis_gb varchar(2) null comment '즉시할인구분',
    dis_rate decimal(12,2) null comment '즉시할인율',
    disp_category_id varchar(9) null comment '전시카테고리',
    divide_mth varchar(2) null comment '할부개월수',
    drt_deli_margin decimal(12,2) null comment '?',
    exclu_charge_yn varchar(2) null comment '추가금여부',
    free_gift_yn varchar(2) null comment '무료사은품',
    handling_charge decimal(12,2) null comment '창고수수료',
    handling_charge_yn varchar(2) null comment '창고수수료여부',
    hs_code varchar(9) null comment 'hs코드',
    lead_time decimal(12,2) null comment '진행시간',
    local_deli_fee decimal(12,2) null comment '현지배송비',
    local_price decimal(12,2) null comment '정가',
    local_sale decimal(12,2) null comment '판매가',
    local_tax_rt decimal(12,2) null comment '현지세율',
    margin decimal(12,2) null comment '마진',
    margin_cd varchar(2) null comment '마진구분',
    pay_mth_cd varchar(2) null comment '결제수단',
    pay_type varchar(2) null comment '결제구분',
    pl_from_dt datetime null comment '프로모션시작일자',
    pl_gbn varchar(2) null comment '프로모션구분',
    pl_to_dt datetime null comment '프로모션종료일자',
    preorder_yn varchar(2) null comment '선주문여부',
    reserve_give decimal(12,2) null comment '보상금',
    sale_price decimal(12,2) null comment '판매가',
    shortage_yn varchar(2) null comment '품절구분',
    standard_price decimal(12,2) null comment '정가',
    tax_deli_yn varchar(2) null comment '?',
    tax_gb varchar(2) null comment '부가세구분',
    template_id varchar(9) null comment '템플릿아이디',
    vendor_tr_gb varchar(2) null comment '배송구분',
    weight decimal(12,2) null comment '무게',
    reg_dt datetime null comment '작성일',
    reg_id int(10) null comment '작성자',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일'
    );

create table if not exists itasrt
(
    assort_id varchar(9) not null comment '상품코드'
    primary key,
    assort_nm varchar(255) null comment '상품명',
    assort_dnm varchar(255) null comment '상품명 ',
    assort_enm varchar(255) null comment '상품명 영문',
    assort_model varchar(255) null comment '상품모델',
    pay_mth_cd varchar(2) null comment '결제구분',
    coupon_yn varchar(2) null comment '쿠폰사용여부',
    pack_yn varchar(2) null comment '포장여부',
    deli_confirm_yn varchar(2) null comment '배송확인',
    deli_mth varchar(2) null comment '배송방법',
    add_deli_gb varchar(2) null comment '추가배송구분',
    deli_charge decimal(12,2) null comment '배송비',
    deli_interval int(10) null comment '배송일자',
    margin decimal(12,2) null comment '마진',
    margin_gb varchar(2) null comment '마진구분',
    margin_app varchar(2) null comment '마진??',
    tax_gb varchar(2) null comment '부가세구분',
    adult_gb varchar(2) null comment '성인사용구분',
    union_apply varchar(2) null comment '합포장여부',
    card_free_gb varchar(2) null comment '무이자구분',
    divide_mth varchar(2) null comment '할부개월수',
    assort_gb varchar(2) null comment '상품구분',
    assort_state varchar(2) null comment '상품상태',
    reason_cd varchar(2) null comment '사유',
    vendor_tr_gb varchar(2) null comment '배송구분',
    site_url text null comment '사이트주소',
    luxury_yn varchar(2) null comment '럭셔리여부',
    as_width decimal(12,2) null comment '넓이',
    as_length decimal(12,2) null comment '길이',
    as_height decimal(12,2) null comment '높이',
    weight decimal(12,2) null comment '무게',
    img_cnt int(10) null comment '이미지수',
    card_limit_yn varchar(2) null comment '카드수량??',
    search_yn varchar(2) null comment '검색가능',
    size_id varchar(2) null comment '사이즈아이디',
    origin varchar(2) null comment '원산지',
    quality varchar(2) null comment '퀄리티',
    lead_time int(10) null comment '진행시간',
    deli_sure varchar(2) null comment '?',
    reserve_yn varchar(2) null comment '예약구분',
    res_sta_dt datetime null comment '예약시작일',
    res_end_dt datetime null comment '예약종료일',
    claim_sure_yn varchar(2) null comment '?',
    default_yn varchar(2) null comment '?',
    recomm_yn varchar(2) null comment '?',
    recomm_cnt int(10) null comment '?',
    recomm_qty int(10) null comment '?',
    bt_mark_yn varchar(2) null comment '?',
    shortage_yn varchar(2) null comment '품절구분',
    sendback_reject_yn varchar(2) null comment '사후반품가능여부',
    set_gb varchar(2) null comment '세트구분',
    user_id varchar(9) null comment '담당MD',
    category_id varchar(9) null comment '내부카테고리',
    brand_id varchar(9) null comment '브랜드',
    disp_category_id varchar(9) null comment '전시카테고리',
    hs_code varchar(9) null comment 'hs코드',
    unit varchar(50) null comment '단위',
    vendor_id varchar(6) null comment '거래처',
    al_assort_id varchar(9) null comment '?',
    drt_sales_gb varchar(2) null comment '직거래구분',
    template_id varchar(9) null comment '템플릿아이디',
    drt_sales_ratio varchar(9) null comment '직거래할인율',
    spe_tax_yn varchar(2) null comment '?',
    pre_item_yn varchar(2) null comment '?',
    srh_exp_yn varchar(2) null comment '?',
    nonsale_yn varchar(2) null comment '할인여부',
    site_gb varchar(2) null comment '사이트구분',
    as_vendor_id varchar(6) null comment '벤더구분',
    deli_nrg_gb varchar(2) null comment '?',
    manufacture_nm varchar(255) null comment '제조사명',
    delay_reward_yn varchar(2) null comment '지연보상여부',
    call_dis_limit decimal(12,2) null comment '?',
    deli_price decimal(12,2) null comment '원가',
    local_price decimal(12,2) null comment '정가',
    init_local_price decimal(12,2) null comment '원가격',
    local_sale decimal(12,2) null comment '판매가',
    esti_price decimal(12,2) null comment '기초금액',
    invoice_nm varchar(255) null comment '인보이스명',
    local_deli_fee decimal(12,2) null comment '현지배송비',
    card_free_yn varchar(2) null comment '무이자여부',
    item_abb_nm varchar(255) null comment '상품??명',
    dis_gb varchar(2) null comment '즉시할인구분',
    dis_rate decimal(12,2) null comment '즉시할인율',
    reserve_give decimal(12,2) null comment '보상금',
    bonus_reserve decimal(12,2) null comment '추가보상금',
    cashbag_point decimal(12,2) null comment '캐시백포인트',
    pl_gbn varchar(2) null comment '프로모션구분',
    pl_from_dt datetime null comment '프로모션시작일자',
    pl_to_dt datetime null comment '프로모션종료일자',
    storage_id varchar(6) null comment '창고',
    option_gb varchar(2) null comment '옵션구분',
    shop_sale_gb varchar(2) null comment '판매구분',
    reg_id int(10) null comment '작성자',
    reg_dt datetime null comment '작성일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일',
    sendback_pay_gb varchar(2) null comment '적립구분',
    sendback_change_yn varchar(2) null comment '적립여부',
    direct_path_nrg_cd varchar(2) null comment '?',
    margin_cd varchar(2) null comment '마진구분',
    direct_path_gb varchar(2) null comment '?',
    res_ship_sta_dt datetime null comment '예약발송시작일',
    res_ship_end_dt datetime null comment '예약발송종료일',
    img_type varchar(2) null comment '이미지타입',
    onlinedisp_yn varchar(2) null comment '전시여부',
    pay_type varchar(2) null comment '결제구분',
    free_gift_yn varchar(2) null comment '무료사은품',
    currency_unit varchar(255) null comment '통화단위',
    dis_start_dt datetime null comment '즉시할인시작일',
    dis_end_dt datetime null comment '즉시할인종료일',
    work_gb varchar(2) null comment '?',
    card_fee decimal(12,2) null comment '수수료율',
    assort_grade varchar(2) null comment '상품등급',
    assort_color varchar(255) null comment '대표색상',
    sell_sta_dt datetime null comment '판매시작일',
    sell_end_dt datetime null comment '판매종료일',
    md_rrp decimal(12,2) null comment '튜닝 : rrp',
    md_tax varchar(10) null comment '튜닝 : 자료',
    md_year varchar(20) null comment '튜닝 : 자료연도',
    md_margin decimal(12,2) null comment '튜닝 : 추가 마진',
    md_vatrate decimal(12,2) null comment '튜닝 : 부가세율',
    md_offline_price decimal(12,2) null comment '튜닝 : 정가(오프라인)',
    md_online_price decimal(12,2) null comment '튜닝 : 판매가(온라인)',
    md_goods_vatrate decimal(12,2) null comment '튜닝 : 상품 마진율',
    buy_where varchar(255) null comment '튜닝 : 구매처',
    buy_tax varchar(2) null comment '튜닝 : 구매',
    buy_supply_discount decimal(12,2) null comment '튜닝 : 공급 할인율',
    buy_rrp_increment decimal(12,2) null comment '튜닝 : rrp 인상률',
    buy_exchange_rate decimal(12,2) null comment '튜닝 : 적용환율',
    md_discount_rate decimal(12,2) null comment '튜닝 : 할인율',
    option_gb_name varchar(100) null comment '옵션구분명',
    option_use_yn varchar(2) null comment '옵션사용여부'
    );

create table if not exists itbrnd
(
    brand_id varchar(9) not null comment '브랜드코드'
    primary key,
    brand_nm varchar(100) null comment '브랜드명',
    brand_enm varchar(100) null comment '브랜드영어명',
    reg_id int(10) null comment '등록자',
    reg_dt datetime null comment '등록일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일'
    );

create table if not exists itcatg
(
    category_id varchar(9) not null comment '카테고리코드'
    primary key,
    eff_end_dt datetime null comment '종료일',
    category_gid varchar(9) null comment '카테고리그룹',
    eff_sta_dt datetime null comment '시작일',
    category_nm varchar(100) null comment '카테고리명',
    category_enm varchar(100) null comment '카테고리영문명',
    disp_nm varchar(100) null comment '전시명',
    disp_order varchar(100) null comment '전시순서',
    up_category_id varchar(9) null comment '상위카테고리',
    lvl int(10) null comment '뎁스',
    is_bottom_yn varchar(2) null comment '말단카테고리여부',
    coupon_use_yn varchar(2) null comment '쿠폰사용여부',
    tstop_yn varchar(2) null comment '사용여부',
    category_gb varchar(2) null comment '카테고리구분',
    inner_ctg_id varchar(9) null comment '내부카테고리',
    order_type varchar(2) null comment '주문타입',
    disp_type varchar(2) null comment '전시타입',
    site_cate_type varchar(2) null comment '사이트카테고리타입',
    global_ctg_id varchar(2) null comment '외부카테고리',
    brand_id varchar(9) null comment '브랜드',
    join_id varchar(9) null comment '제휴구분',
    disp_gb varchar(2) null comment '전시구분',
    template_id varchar(9) null comment '템플릿아이디',
    user_id int(10) null comment '작업자',
    reg_id int(10) null comment '등록자',
    reg_dt datetime null comment '등록일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일',
    root_category_id varchar(9) null comment '루트카테고리',
    link_url varchar(100) null comment '링크'
    );

create index ITCATG_IDX_01
	on itcatg (up_category_id);

create table if not exists ititmc
(
    storage_id varchar(6) not null comment '창고',
    assort_id varchar(9) not null comment '상품코드',
    item_id varchar(4) not null comment '옵션코드',
    item_grade varchar(2) not null comment '등급',
    eff_end_dt datetime not null comment '종료일',
    eff_sta_dt datetime not null comment '시작일',
    stock_gb varchar(2) null comment '재고구분',
    ship_indicate_qty int(10) null comment '출고예정수량',
    qty int(10) null comment '재고수량',
    stock_amt decimal(12,2) null comment '재고원가',
    vendor_id varchar(6) null comment '거래처',
    site_gb varchar(2) null comment '사이트구분',
    reg_id int(10) null comment '작성일',
    reg_dt datetime null comment '작성자',
    upd_id int(10) null comment '수정일',
    upd_dt datetime null comment '수정자',
    primary key (storage_id, assort_id, item_id, item_grade, eff_end_dt, eff_sta_dt)
    );

create table if not exists ititmd
(
    seq int auto_increment comment 'seq'
    primary key,
    assort_id varchar(9) not null comment '상품코드',
    item_id varchar(4) not null comment '옵션코드',
    eff_end_dt datetime not null comment '종료일자',
    eff_sta_dt datetime not null comment '시작일자',
    short_yn varchar(2) null comment '품절여부',
    reg_id int(10) null comment '작성자',
    reg_dt datetime null comment '작성일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일'
    );

create table if not exists ititmm
(
    assort_id varchar(9) not null comment '상품코드',
    item_id varchar(4) not null comment '옵션코드',
    item_nm varchar(255) null comment '상품명',
    short_yn varchar(2) null comment '품절여부',
    min_cnt int(10) null comment '최소수량',
    max_cnt int(10) null comment '최대수량',
    day_deli_cnt int(10) null comment '일일배송수량',
    tot_deli_cnt int(10) null comment '전체배송수량',
    variation_gb1 varchar(2) null comment '속성구분1',
    variation_seq1 varchar(4) null comment '속성번호1',
    variation_gb2 varchar(2) null comment '속성구분2',
    variation_seq2 varchar(4) null comment '속성구분2',
    set_yn varchar(2) null comment '세트구분',
    reg_id int(10) null comment '작성자',
    reg_dt datetime null comment '작성일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일',
    order_lmt_yn varchar(2) null comment '제한수량여부',
    order_lmt_cnt int(10) null comment '제한수량',
    add_price decimal(12,2) null comment '추가금액',
    primary key (assort_id, item_id)
    );

create table if not exists ititmt
(
    storage_id varchar(6) not null comment '창고',
    assort_id varchar(9) not null comment '상품코드',
    item_id varchar(4) not null comment '옵션코드',
    item_grade varchar(2) not null comment '등급',
    eff_end_dt datetime not null comment '종료일',
    eff_sta_dt datetime not null comment '시작일',
    stock_gb varchar(2) null comment '재고구분',
    temp_indicate_qty int(10) null comment '출고예정수량',
    temp_qty int(10) null comment '입고예정수량',
    stock_amt decimal(12,2) null comment '재고원가',
    vendor_id varchar(6) null comment '거래처',
    site_gb varchar(2) null comment '사이트구분',
    reg_id int(10) null comment '작성일',
    reg_dt datetime null comment '작성자',
    upd_id int(10) null comment '수정일',
    upd_dt datetime null comment '수정자',
    primary key (storage_id, assort_id, item_id, item_grade, eff_end_dt, eff_sta_dt)
    );

create table if not exists itlkag
(
    assort_id varchar(9) not null comment '상품코드',
    add_goods_id varchar(9) not null comment '추가상품코드',
    eff_sta_dt datetime not null comment '시작일',
    eff_end_dt datetime not null comment '종료일',
    reg_id int(10) null comment '작성자',
    reg_dt datetime null comment '작성일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일',
    primary key (assort_id, add_goods_id, eff_sta_dt, eff_end_dt)
    );

create table if not exists itmmot
(
    assort_id varchar(9) not null comment '품목코드',
    option_text_id varchar(4) not null comment '옵션순번',
    option_nm varchar(255) null comment '옵션명',
    add_price decimal(12,2) null comment '추가금',
    del_yn varchar(2) null comment '삭제여부',
    reg_id int(10) null comment '작성자',
    reg_dt datetime null comment '작성일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일',
    must_fl varchar(2) default 'n' null,
    input_limit int(5) null,
    primary key (assort_id, option_text_id)
    );

create table if not exists itvari
(
    assort_id varchar(9) not null comment '상품코드',
    seq varchar(4) not null comment '옵션코드',
    option_gb varchar(2) null comment '옵션구분',
    img_yn varchar(2) null comment '이미지여부',
    option_nm varchar(255) null comment '옵션명',
    variation_gb varchar(2) null comment '속성구분',
    del_yn varchar(2) null comment '삭제여부',
    reg_id int(10) null comment '작성자',
    reg_dt datetime null comment '작성일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일',
    primary key (assort_id, seq)
    );

create table if not exists lsdpds
(
    seq int auto_increment comment 'seq'
    primary key,
    deposit_no varchar(9) null comment '입고번호',
    deposit_seq varchar(4) null comment '입고순번',
    eff_end_dt datetime null comment '종료일자',
    eff_sta_dt datetime null comment '시작일자',
    deposit_status varchar(2) null comment '입고상태',
    reg_id int(10) null comment '작성자',
    reg_dt datetime null comment '작성일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일'
    );

create table if not exists lsdpsd
(
    deposit_no varchar(9) not null comment '입고번호',
    deposit_seq varchar(4) not null comment '입고순번',
    assort_id varchar(9) null comment '상품코드',
    item_id varchar(4) null comment '속성코드',
    item_grade varchar(2) null comment '등급(정상품,하품)',
    extra_cls_cd varchar(2) null comment '구분(?)',
    deposit_qty int null comment '입고수량',
    deli_price decimal(12,2) null comment '원가',
    sale_price decimal(12,2) null comment '판매가',
    extra_unitcost decimal(12,2) null comment '단가',
    extra_cost decimal(12,2) null comment '원가',
    extra_qty int(10) null comment '입고수량',
    finish_yymm datetime null comment '마감일자',
    deposit_type varchar(2) null comment '입고타입',
    site_gb varchar(2) null comment '사이트구분',
    vendor_id varchar(6) null comment '거래처',
    s_storage_cd varchar(6) null comment '창고코드',
    min_deposit_no varchar(9) null comment '대응입고번호',
    min_deposit_seq varchar(4) null comment '대응입고순번',
    input_no varchar(9) null comment '관련번호',
    input_seq varchar(4) null comment '관련순번',
    reg_id int(10) null comment '작성자',
    reg_dt datetime null comment '작성일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일',
    exc_app_dt datetime null comment '입고일자',
    primary key (deposit_no, deposit_seq)
    );

create table if not exists lsdpsm
(
    deposit_no varchar(9) not null comment '입고번호'
    primary key,
    deposit_dt datetime null comment '입고일자',
    deposit_gb varchar(2) null comment '입출고구분',
    site_gb varchar(2) null comment '사이트',
    vendor_id varchar(6) null comment '벤더',
    finish_yymm datetime null comment '마감일자',
    deposit_type varchar(2) null comment '입출고타입',
    store_cd varchar(6) null comment '입출고창고',
    reg_id int(10) null comment '작성자',
    reg_dt datetime null comment '작성일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일',
    deposit_vendor_id varchar(9) null comment '입고거래처'
    );

create table if not exists lsdpsp
(
    deposit_plan_id varchar(9) not null comment '입고예정번호'
    primary key,
    sm_reservation_dt datetime null comment '입고예정일',
    purchase_plan_qty int(10) null comment '입고예정수량',
    purchase_take_qty int(10) null comment '실 입고수량',
    assort_id varchar(9) null comment '상품코드',
    item_id varchar(4) null comment '옵션코드',
    plan_status varchar(2) null comment '입고예정상태',
    order_id varchar(9) null comment '주문번호',
    order_seq varchar(4) null comment '주문순번',
    purchase_no varchar(9) null comment '발주번호',
    purchase_seq varchar(4) null comment '발주순번',
    plan_chg_reason varchar(2) null comment '계획변경사유',
    claim_item_yn varchar(2) null comment '클레임여부',
    reg_id int(10) null comment '등록자',
    reg_dt datetime null comment '등록일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일'
    );

create table if not exists lsdpss
(
    seq int auto_increment comment 'seq'
    primary key,
    deposit_no varchar(9) null,
    eff_end_dt datetime null,
    deposit_status varchar(2) null,
    eff_sta_dt datetime null,
    reg_id int(10) null,
    reg_dt datetime null,
    upd_id int(10) null,
    upd_dt datetime null
    );

create table if not exists lspchb
(
    seq int auto_increment comment 'seq'
    primary key,
    purchase_no varchar(9) null comment '발주번호',
    purchase_seq varchar(4) null comment '발주순번',
    eff_end_dt datetime null comment '종료일',
    eff_sta_dt datetime null comment '시작일',
    purchase_status varchar(2) null comment '발주상태',
    cancel_gb varchar(2) null comment '취소사유',
    reg_id int(10) null comment '등록자',
    reg_dt datetime null comment '등록일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일'
    );

create table if not exists lspchd
(
    purchase_no varchar(9) not null comment '발주번호',
    purchase_seq varchar(4) not null comment '발주순번',
    assort_id varchar(9) null comment '상품코드',
    item_id varchar(4) null comment '속성코드',
    purchase_qty int(10) null comment '발주수량',
    purchase_unit_amt decimal(12,2) null comment '발주단가(개당)',
    purchase_item_amt decimal(12,2) null comment '발주금액',
    item_grade varchar(2) null comment '등급(정상품,하품)',
    vat_gb varchar(2) null comment '부가세구분',
    set_gb varchar(2) null comment '세트구분',
    mailsend_yn varchar(2) null comment '메일전송여부',
    memo text null comment '메모',
    site_gb varchar(2) null comment '사이트구분',
    vendor_id varchar(6) null comment '거래처구분',
    ra_no varchar(100) null comment '사용x',
    item_amt decimal(12,2) null comment '상품가',
    new_item_amt decimal(12,2) null comment '수정_상품가',
    trans_amt decimal(12,2) null comment '현지배송료',
    new_trans_amt decimal(12,2) null comment '수정_현지배송료',
    tax_amt decimal(12,2) null comment '현지세금',
    new_tax_amt decimal(12,2) null comment '수정_현지세금',
    sale_amt decimal(12,2) null comment '할인가',
    new_sale_amt decimal(12,2) null comment '수정_할인가',
    order_id varchar(9) null comment '주문번호',
    order_seq varchar(4) null comment '주문순번',
    deposit_no varchar(9) null comment '입고번호',
    deposit_seq varchar(4) null comment '입고순번',
    set_ship_id varchar(9) null comment '세트번호',
    set_ship_seq varchar(4) null comment '세트순번',
    site_order_no varchar(100) null comment '사이트주문번호',
    reg_id int(10) null comment '등록자',
    reg_dt datetime null comment '등록일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일',
    primary key (purchase_no, purchase_seq)
    );

create table if not exists lspchm
(
    purchase_no varchar(9) not null comment '발주번호'
    primary key,
    purchase_dt datetime null comment '발주일자',
    eff_end_dt datetime null comment '종료일자',
    purchase_status varchar(2) null comment '발주상태',
    purchase_remark text null comment '발주비고',
    site_gb varchar(2) null comment '사이트구분',
    vendor_id varchar(6) null comment '거래처구분',
    dealtype_cd varchar(2) null comment '거래구분',
    site_order_no varchar(100) null comment '사이트주문번호',
    site_track_no varchar(100) null comment '사이트 배송트래킹 번호	',
    purchase_cust_nm varchar(100) null comment '발주고객명	',
    local_price decimal(12,2) null comment '정가',
    new_local_price decimal(12,2) null comment '수정 정가',
    local_deli_fee decimal(12,2) null comment '현지배송지',
    new_local_deli_fee decimal(12,2) null comment '수정 현지배송비',
    local_tax decimal(12,2) null comment '현지세금',
    new_local_tax decimal(12,2) null comment '수정 현지세금',
    dis_price decimal(12,2) null comment '할인가',
    new_dis_price decimal(12,2) null comment '수정 할인가',
    card_id varchar(2) null comment '사용카드(사용x)',
    purchase_gb varchar(2) null comment '발주구분',
    purchase_vendor_id varchar(9) null comment '발주 구매처',
    affil_vd_id varchar(9) null comment '사용x',
    store_cd varchar(6) null comment '물류센터',
    o_store_cd varchar(6) null comment '원 물류센터',
    terms varchar(100) null comment 'terms',
    delivery varchar(100) null comment 'delivery',
    payment varchar(100) null comment 'payment',
    carrier varchar(100) null comment 'carrier',
    reg_id int(10) null comment '등록자',
    reg_dt datetime null comment '등록일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일	'
    );

create table if not exists lspchs
(
    seq int auto_increment comment 'seq'
    primary key,
    purchase_no varchar(9) null comment '발주번호',
    eff_end_dt datetime null comment '종료일',
    purchase_status varchar(2) null comment '발주상태',
    eff_sta_dt datetime null comment '시작일',
    reg_id int(10) null comment '등록자',
    reg_dt datetime null comment '등록일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일'
    );

create table if not exists lsshpd
(
    ship_id varchar(9) not null comment '출고번호',
    ship_seq varchar(4) not null comment '출고순번',
    assort_id varchar(9) null comment '품목코드',
    item_id varchar(4) null comment '상품코드',
    ship_vendor_id varchar(9) null comment '출고거래처',
    ship_indicate_qty int(10) null comment '출고지시수량',
    ship_qty int(10) null comment '출고수량',
    vendor_deal_cd varchar(2) null comment '거래타입',
    vat_gb varchar(2) null comment '부가세',
    order_id varchar(17) null comment '주문번호',
    order_seq varchar(4) null comment '주문순번',
    ship_gb varchar(2) null comment '출고구분',
    site_gb varchar(2) null comment '사이트구분',
    vendor_id varchar(6) null comment '거래처번호',
    rack_number varchar(6) null comment '랙번호',
    customs_tax decimal(12,2) null comment '관세',
    exc_app_dt datetime null comment '관련일자(출고품목의 재고일자)',
    order_discount decimal(12,2) null comment '주문할인액',
    sale_cost decimal(12,2) null comment '원가',
    local_price decimal(12,2) null comment '현지금액',
    local_deli_fee decimal(12,2) null comment '현지배송비',
    local_tax decimal(12,2) null comment '현지세금',
    dis_price decimal(12,2) null comment '할인금액',
    o_storage_id varchar(6) null comment '원출고창고',
    reg_id int(10) null comment '등록자',
    reg_dt datetime default CURRENT_TIMESTAMP null comment '등록일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime default CURRENT_TIMESTAMP null comment '수정일',
    primary key (ship_id, ship_seq)
    );

create table if not exists lsshpm
(
    ship_id varchar(9) not null comment '출고번호'
    primary key,
    ship_order_gb varchar(2) null comment '출고주문구분',
    ship_times int(10) null comment '출고차수',
    ship_status varchar(2) null comment '출고상태',
    deli_id int(10) null comment '배송주소',
    ship_item_cnt int(10) null comment '출고건수',
    receipt_dt datetime null comment '접수일자',
    storage_id varchar(6) null comment '출고창고',
    instruct_dt datetime null comment '패킹일자',
    apply_day datetime null comment '출고일자',
    master_ship_gb varchar(2) null comment '마스터출고구분(?)',
    memo text null comment '메모',
    site_gb varchar(2) null comment '사이트',
    vendor_id varchar(6) null comment '벤더',
    del_method varchar(2) null comment '배송방법',
    remat_gb varchar(2) null comment '재매치구분',
    ship_gb varchar(2) null comment '출고구분',
    item_grade varchar(2) null comment '아이템등급',
    deli_company_cd varchar(4) null comment '배송사',
    order_id varchar(17) null comment '주문번호',
    reg_id int(10) null comment '등록자',
    reg_dt datetime default CURRENT_TIMESTAMP null comment '등록일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime default CURRENT_TIMESTAMP null comment '수정일',
    upload_dt datetime null comment '업로드일자'
    );

create table if not exists lsshps
(
    seq int auto_increment comment 'seq'
    primary key,
    ship_id varchar(9) not null comment '출고번호',
    eff_end_dt datetime not null comment '종료일',
    eff_sta_dt datetime not null comment '시작일',
    ship_status varchar(2) not null comment '출고상태',
    ship_indicate_userid int(10) null comment '출고지시자',
    ship_userid int(10) null comment '출고자',
    reg_id int(10) null comment '등록자',
    reg_dt datetime default CURRENT_TIMESTAMP null comment '등록일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime default CURRENT_TIMESTAMP null comment '수정일'
    );

create table if not exists product
(
    id bigint auto_increment
    primary key,
    product_name varchar(255) null,
    product_price varchar(255) null
    );

create table if not exists sequence_data
(
    sequence_name varchar(100) not null
    primary key,
    sequence_increment int(11) unsigned default 1 not null,
    sequence_min_value int(11) unsigned default 1 not null,
    sequence_max_value bigint unsigned default 18446744073709551615 not null,
    sequence_cur_value bigint unsigned default 1 null,
    sequence_cycle tinyint(1) default 0 not null
    )
    engine=MyISAM;

create table if not exists tags
(
    id varchar(255) not null
    primary key,
    name varchar(255) not null
    );

create table if not exists tb_gb1
(
    id varchar(255) not null
    primary key,
    name varchar(255) null
    )
    charset=utf8mb4;

create table if not exists tb_member
(
    cust_id int(10) auto_increment comment '일련번호'
    primary key,
    cust_nm varchar(100) not null,
    cust_email varchar(100) not null,
    login_id varchar(100) not null,
    login_pw varchar(255) not null,
    channel_gb varchar(3) null,
    cust_gb varchar(3) null,
    cust_grade varchar(3) null,
    cust_status varchar(3) null,
    cust_tel varchar(20) null,
    cust_hp varchar(20) null,
    cust_zipcode varchar(7) null,
    cust_addr1 text null,
    cust_addr2 text null,
    reg_id int unsigned not null,
    reg_dt datetime default CURRENT_TIMESTAMP null,
    upd_id int unsigned not null,
    upd_dt datetime default CURRENT_TIMESTAMP null
    );

create table if not exists tb_member_address
(
    deli_id int(10) auto_increment comment '일련번호'
    primary key,
    cust_id int(10) not null,
    deli_nm varchar(255) null,
    deli_gb varchar(3) null,
    deli_tel varchar(20) null,
    deli_hp varchar(20) null,
    deli_zipcode varchar(7) null,
    deli_addr1 text null,
    deli_addr2 text null,
    reg_id int unsigned not null,
    reg_dt datetime default CURRENT_TIMESTAMP null,
    upd_id int unsigned not null,
    upd_dt datetime default CURRENT_TIMESTAMP null
    );

create table if not exists tb_order_detail
(
    order_id varchar(17) not null,
    order_seq varchar(4) not null,
    status_cd varchar(3) not null,
    assort_gb varchar(3) not null,
    assort_id varchar(9) null,
    item_id varchar(4) null,
    goods_nm varchar(255) null,
    option_info varchar(255) null,
    set_gb varchar(2) null,
    set_order_id varchar(17) null,
    set_order_seq varchar(4) null,
    qty int(10) null,
    item_amt decimal(12,2) null,
    goods_price decimal(12,2) null,
    sale_price decimal(12,2) null,
    goods_dc_price decimal(12,2) null,
    member_dc_price decimal(12,2) null,
    coupon_dc_price decimal(12,2) null,
    admin_dc_price decimal(12,2) null,
    dc_sum_price decimal(12,2) null,
    deli_price decimal(12,2) null,
    deli_method varchar(3) null,
    channel_order_no varchar(20) null,
    channel_order_seq varchar(20) null,
    reg_id int unsigned not null,
    reg_dt datetime default CURRENT_TIMESTAMP null,
    upd_id int unsigned not null,
    upd_dt datetime default CURRENT_TIMESTAMP null,
    storage_id varchar(6) null,
    primary key (order_id, order_seq)
    )
    comment '주문디테일';

create table if not exists tb_order_history
(
    seq int(10) auto_increment comment '일련번호'
    primary key,
    order_id varchar(17) not null,
    order_seq varchar(4) not null,
    status_cd varchar(3) not null,
    last_yn varchar(3) not null,
    eff_start_dt datetime default CURRENT_TIMESTAMP null,
    eff_end_dt datetime null,
    reg_id int unsigned not null,
    reg_dt datetime default CURRENT_TIMESTAMP null,
    upd_id int unsigned not null,
    upd_dt datetime default CURRENT_TIMESTAMP null
    );

create table if not exists tb_order_master
(
    order_id varchar(17) not null
    primary key,
    first_order_id varchar(17) not null,
    order_date datetime not null,
    order_status varchar(3) not null,
    channel_gb varchar(3) not null,
    cust_id int(10) null,
    deli_id int(10) null,
    order_amt decimal(12,2) null,
    receipt_amt decimal(12,2) null,
    first_order_gb varchar(3) null,
    order_gb varchar(3) null,
    channel_order_no varchar(20) not null,
    cust_pcode varchar(30) null,
    order_memo text null,
    reg_id int unsigned not null,
    reg_dt datetime default CURRENT_TIMESTAMP null,
    upd_id int unsigned not null,
    upd_dt datetime default CURRENT_TIMESTAMP null
    )
    comment '주문마스터';

create table if not exists testenum
(
    seq int auto_increment comment 'seq'
    primary key,
    assort_gb varchar(2) null comment '구분',
    assort_yn varchar(2) null comment 'yn',
    brand_id varchar(9) null
    )
    charset=utf8mb4;

create table if not exists testenum2
(
    seq int auto_increment comment 'seq'
    primary key,
    assort_gb varchar(2) default '01' null comment '구분',
    assort_yn varchar(2) default '02' null comment 'yn',
    brand_id varchar(9) null
    )
    charset=utf8mb4;

create table if not exists tmitem
(
    channel_gb varchar(3) not null comment '채널구분',
    assort_id varchar(9) not null comment '상품코드',
    item_id varchar(4) not null comment '옵션코드',
    eff_sta_dt datetime not null comment '시작일',
    eff_end_dt datetime not null comment '종료일',
    short_yn varchar(2) null comment '품절구분',
    variation_gb1 varchar(2) null comment '속성구분1',
    variation_seq1 varchar(4) null comment '속성번호1',
    variation_gb2 varchar(2) null comment '속성구분2',
    variation_seq2 varchar(4) null comment '속성구분2',
    channel_goods_no varchar(20) null comment '채널상품코드',
    channel_options_no varchar(20) null comment '채널옵션코드',
    reg_id int(10) null comment '작성자',
    reg_dt datetime null comment '작성일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일',
    primary key (channel_gb, assort_id, item_id, eff_sta_dt, eff_end_dt)
    );

create table if not exists tmmapi
(
    channel_gb varchar(3) not null comment '채널구분',
    assort_id varchar(9) not null comment '상품코드',
    assort_nm varchar(255) null comment '상품명',
    channel_goods_no varchar(20) null comment '제휴상품코드',
    standard_price decimal(12,2) null comment '정가',
    sale_price decimal(12,2) null comment '판매가',
    deli_gb varchar(10) null comment '배송구분',
    deli_price decimal(12,2) null comment '배송비',
    deli_max_price decimal(12,2) null comment '배송기준금액',
    shortage_yn varchar(2) null comment '품절여부',
    up_join_category_id varchar(9) null comment '적용 제휴카테고리아이디',
    up_assort_nm varchar(255) null comment '적용 상품명',
    up_standard_price decimal(12,2) null comment '적용 정가',
    up_sale_price decimal(12,2) null comment '적용 판매가',
    up_deli_gb varchar(2) null comment '적용 배송구분',
    up_deli_price decimal(12,2) null comment '적용 배송비',
    up_deli_max_price decimal(12,2) null comment '적용 배송기준금액',
    up_shortage_yn varchar(2) null comment '적용 품절여부',
    up_join_maker_id varchar(9) null comment '적용 제조사',
    upload_type varchar(2) null comment '업로드 구분',
    upload_yn varchar(2) null comment '업로드 여부',
    upload_dt datetime null comment '업로드 일자',
    upload_rmk text null comment '업로드 비고',
    join_status varchar(2) null comment '업로드 상태',
    error_msg text null comment '에러메시지',
    reg_id int(10) null comment '작성자',
    reg_dt datetime null comment '작성일',
    upd_id int(10) null comment '수정자',
    upd_dt datetime null comment '수정일',
    primary key (channel_gb, assort_id)
    );

create table if not exists users
(
    id varchar(255) not null
    primary key,
    username varchar(255) null,
    password varchar(255) null,
    email varchar(255) null,
    bio text null,
    image varchar(511) null,
    constraint email
    unique (email),
    constraint username
    unique (username)
    );