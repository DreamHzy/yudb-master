package com.ynzyq.yudbadmin.util.ylWallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ynzyq.yudbadmin.util.ylWallet.msg.BaseResult;
import com.ynzyq.yudbadmin.util.ylWallet.msg.GetPlugRandomKeyResult;
import com.ynzyq.yudbadmin.util.ylWallet.msg.WalletRequest;
import com.ynzyq.yudbadmin.util.ylWallet.msg.WalletResponse;
import com.ynzyq.yudbadmin.util.ylWallet.parm.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 钱包相关调用方法
 */
@Slf4j
public class WalletUtil {

    /**
     * 开户资料zip文件
     */
    public final static String ZIP_PATH = "/usr/local/java/reren/";

    //总店id和前面名称
    public final static String ZONG_DAIAN_WALLET_ID = "2061102700094450654";
    public final static String ZONG_DAIAN_WALLET_NAME = "北京鱼你在一起品牌管理有限公司";

    public final static String CERT = "CERT";

    public final static String PWD = "PWD";

    public final static String CODE = "00000";

    /**
     * 支付密码相关字段
     */
//    private final static String ENCRYPTED_PWD = "ewHzgdQ+GyAFM6MyN1gSndKgd4wkJhGLsJRJEqzjZ2+QlfxilkqZq9K6r07qv3mEoIOmOEqRApRUc8T3X+ysUmQ7zIjKIWCN1R3V2XINVgibm1tFeabGh6zzRlKehY/lCYZlJ/QFx5oSRwsNJQx2EdXIg8+ySL1Cjim/Zkdn78sV0mRPqARM1vQj3LuVNCPKV8P+PIl3yJfpwya0NA5jBI7vT1isx18yhNvs+1YyVDIbEQtx2cy3zysjdux9w/Ho6/aMhWM++fKd2dO1Q3mi9Y0jsPT80ah62NzH+DPYVC4J42APIL2DkHAJ+tdon/XG+Cu+wVKrqVCfQv8IUufDwg==";
//    private final static String PLUG_RAND_KEY = "20180921163356ul2777472034t2iw2o";

    /**
     * 文件资料地方测试用
     */
    public final static Queue<String> plugRandKeyQueue = new ArrayDeque<>();
    public final static String PLUG_RAND_KEY_QUEUE_SIZE = "5";
    public final static String ENCRYPT_TYPE = "2";

    public final static PrivateKey MCT_WALLET_TRANS_CERT;
    /**
     * 钱包交易证书
     */
    public final static String MCT_WALLET_TRANS_CERT_PATH = "/usr/local/java/reren/beijingyu/zhifumiyao.pfx";
    /**
     * 钱包交易证书密码
     */
    public final static String MCT_WALLET_TRANS_CERT_PWD = "WLX2009w.";

    static {
        MCT_WALLET_TRANS_CERT = CryptoUtils.initPrivateKeyFromFile(new File(MCT_WALLET_TRANS_CERT_PATH), MCT_WALLET_TRANS_CERT_PWD);
    }

    public static void disableSslVerification() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }
            };
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 请求方标识
     */
    private final static String ISSR_ID = "224ebe963b3445afa675bc053b11d9f0";
    /**
     * 转账验证方式 PWD-支付密码 CERT-交易证书
     */
    private static String verifyType = "PWD";

//
//    /**
//     * 文件上传获取id方法
//     */
//    public static String fileId(File file) throws IOException {
//        // 获取文件上传地址
//        String uploadUrl = requestUploadUrl();
//        // 上传文件，获取文件ID
//        return uploadZipFile(uploadUrl + "&fileName=demo.zip", ZIP_PATH);
//    }

    /**
     * 获取文件上传地址
     *
     * @return
     * @throws IOException
     */
    public static String requestUploadUrl() throws IOException {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("jumpType", "12");
        JSONObject ret = GneteGatewayRequestUtils.callGneteGateway("gnete.wextbc.WextbcTradeRpcService.applyTicket", JSON.toJSONString(params));
        System.out.println("ret---------------l: " + ret);
        return ret.getJSONObject("response").getString("jumpUrl");
    }

    public static String uploadZipFile(String url, String path) throws IOException {
        String ret = upload(url, path);
        return JSON.parseObject(ret).getJSONObject("result").getString("fileId");
    }

    public static String upload(String url, String filePath) throws IOException {
        System.out.println("Upload " + filePath + " -> " + url);
        String ret = HttpUtils.post(url, new FileInputStream(filePath), "application/zip");
        System.out.println("Upload success: " + ret);
        return ret;
    }


    /**
     * H5相关接口(目前可使用有以下操作，后续会添加)
     * * 11、H5 企业注册开户
     * * 3、 重置支付密码(C)
     * * 2、 修改支付密码(B/C)
     *
     * @param h5Parm
     * @return
     * @throws IOException
     */
    public static JSONObject h5(H5Parm h5Parm) throws IOException {
        String msg = JSON.toJSONString(h5Parm);
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wextbc.WextbcTradeRpcService.applyTicket", msg);
    }


    /**
     * 验证凭证接口
     */

    public static JSONObject validateTicket(ValidateTicketParam validateTicketParam) throws IOException {
        String msg = JSON.toJSONString(validateTicketParam);
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wextbc.WextbcTradeRpcService.validateTicket", msg);
    }


    /**
     * 外部用户查询电子账户 ID
     * 主要应用于 C 端 H5 开户后，
     * 使用接入方的用户 ID（简称：外部用户 ID）查询电子账 ID，
     * 此业务通过企业网关发起请求操作，在扩展业务中心获取到请求方的 APPID 及外部户
     * 用户 ID 进行查询符合条件的电子账户 ID
     */
    public static JSONObject queryWalletId(QueryWalletIdParam queryWalletIdParam) throws IOException {
        String msg = JSON.toJSONString(queryWalletIdParam);
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wextbc.WextbcQueryRpcService.queryWalletId", msg);
    }

    /**
     * 查询注册登记信息
     * 主要应用于 B 端 H5/PC 开户后，使用登记手机号或注册号查询注册登记信息。
     */
    public static JSONObject queryRegInfo(QueryRegInfoParam queryWalletIdParam) throws IOException {
        String msg = JSON.toJSONString(queryWalletIdParam);
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wextbc.EeepmsRegQueryRPCRpcService.queryRegInfo", msg);
    }


    /**
     * 重置支付密码
     */
    public static JSONObject resetBtypeAcctPwd(ResetBtypeAcctPwdParam resetBtypeAcctPwdParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.resetBtypeAcctPwd", initBizContent(resetBtypeAcctPwdParam));
    }


    /**
     * 查询账户余额
     */
    public static JSONObject queryAcctBal(QueryAcctBalParam queryAcctBalParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102QueryRpcService.queryAcctBal", initBizContent(queryAcctBalParam));
    }

    /**
     * 通过钱包id查询电子账户信息
     */
    public static JSONObject queryAcctInfo(QueryAcctInfoParam queryAcctBalParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102QueryRpcService.queryAcctInfo", initBizContent(queryAcctBalParam));
    }

    /**
     * 查询账户关联信息
     */
    public static JSONObject queryAcctRelatedInfo(QueryAcctRelatedInfoParam queryAcctRelatedInfoParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102QueryRpcService.queryAcctRelatedInfo", initBizContent(queryAcctRelatedInfoParam));
    }

    /**
     * 单个钱包转账
     * 可以在 B 端或 C 端电子账户之间进行转账。
     *
     * @throws IOException
     */
    public static JSONObject transfer(TransferParam transferParam, String type, String pwd) throws IOException {
        setTradeWayFeilds(transferParam, transferParam.getFromWalletId(), type, pwd, "");
        JSONObject ret = GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.transfer", initBizContent(transferParam));
        return ret;
    }


    /**
     * 查询单个交易结果
     */
    public static JSONObject queryTransResult(QueryTransResultParam queryTransResultParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102QueryRpcService.queryTransResult", initBizContent(queryTransResultParam));

    }

//    /**
//     * 异步提现
//     */
//    public static JSONObject asyncWithdraw(AsyncWithdrawParam asyncWithdrawParam, String type, String pwd, String plugRandKey) throws IOException {
//        setTradeWayFeilds(asyncWithdrawParam, asyncWithdrawParam.getWalletId(), type, pwd, plugRandKey);
//        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.asyncWithdraw", initBizContent(asyncWithdrawParam));
//    }


    /**
     * 批量转账请求
     */
    public static JSONObject batchTransfer(BatchTransferParam batchTransferParam, String type, String pwd,String plugRandKey) throws IOException {
        setTradeWayFeilds(batchTransferParam, batchTransferParam.getFromWalletId(), type, pwd, plugRandKey);
        JSONObject ret = GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.batchTransfer", initBizContent(batchTransferParam));
        return ret;
    }

    /**
     * 批量转账查询
     */
    public static JSONObject queryBatchTransResult(QueryBatchTransResultParam queryTransResultParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102QueryRpcService.queryBatchTransResult", initBizContent(queryTransResultParam));

    }

    /**
     * 查询交易明细
     */
    private static JSONObject queryTransList(QueryTransListParam queryTransListParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102QueryRpcService.queryTransList", initBizContent(queryTransListParam));

    }

    /**
     * 账单查询
     */
    public static JSONObject queryBillInfo(QueryBillInfoParam queryBillInfoParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102QueryRpcService.queryBillInfo", initBizContent(queryBillInfoParam));

    }

    /**
     * 手续费费率查询
     * 电子账户进行提现、转账等交易前，可以调用此接口查询配置的手续费规则和最大可
     * 操作金额。
     */
    private static JSONObject queryFeeRate(QueryFeeRateParam queryFeeRateParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102QueryRpcService.queryFeeRate", initBizContent(queryFeeRateParam));
    }

    /**
     * 手续费查询
     * 电子账户进行提现、转账等交易前，可以调用此接口查询查询手续费的金额。
     */
    private static JSONObject queryFee(QueryFeeParam queryFeeParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102QueryRpcService.queryFee", initBizContent(queryFeeParam));
    }

    /**
     * 添加关联分账方
     */
    public static JSONObject addProfitSharingMerRel(AddProfitSharingMerRelParam addProfitSharingMerRelParam, String type, String pwd) throws IOException {
        setTradeWayFeilds(addProfitSharingMerRelParam, addProfitSharingMerRelParam.getMerWalletId(), type, pwd, "");
        JSONObject ret = GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.addProfitSharingMerRel", initBizContent(addProfitSharingMerRelParam));
        return ret;
    }

    /**
     * 查询关联分账方
     */
    public static JSONObject queryProfitSharingMerRel(QueryProfitSharingMerRelParam queryProfitSharingMerRelParam, String type, String pwd) throws IOException {
        setTradeWayFeilds(queryProfitSharingMerRelParam, queryProfitSharingMerRelParam.getRelMerWalletId(), type, pwd, "");
        JSONObject ret = GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102QueryRpcService.queryProfitSharingMerRel", initBizContent(queryProfitSharingMerRelParam));
        return ret;
    }


    /**
     * 分页查询好支付原交易
     */
    public static JSONObject pageQueryHzfOriTrans(PageQueryHzfOriTransParam pageQueryHzfOriTransParam, String type, String pwd) throws IOException {
        setTradeWayFeilds(pageQueryHzfOriTransParam, pageQueryHzfOriTransParam.getPlatformWalletId(), type, pwd, "");
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102QueryRpcService.pageQueryHzfOriTrans", initBizContent(pageQueryHzfOriTransParam));
    }


    /**
     * 支付交易确认结算
     * 好支付/担保支付完成支付流程后，调用此接口进行分账结算。支持多笔结算，每笔结
     * 算时支持分账，可上送分账明细。
     * 当业务类型为好支付时，必须上送规则 ID 和分账信息。
     * 当业务类型为担保支付时：
     * （1）如果担保支付订单未做过结算且需要撤销，可调用此接口，结算交易金额为 0 及
     * 订单完结为 Y。
     * （2）如订单是担保合并支付订单，请求参数中订单号上送的为具体订单号而非合并支
     * 付订单号。
     * （3）如果担保支付业务类型结算，原交易订单号填写担保支付订单号。
     * （4）未上送分账规则 ID 时，分账方只能为平台钱包 ID，结算到平台钱包 ID。如已上
     * 送分账规则，则分账方和分账金额需要满足分账规则限制。
     * （5) 同一个担保支付订单号，只允许在同一规则下结算。
     * （6）每笔确认时可选择是否完结该笔订单，如选择完结时待结算金额>0，则将剩余未
     * 清算资金退回原买家电子账户。订单已完成，不允许再次结算。
     */
    public static JSONObject paySettConfirm(PaySettConfirmParam paySettConfirmParam) throws IOException {
        JSONObject ret = GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.paySettConfirm", initBizContent(paySettConfirmParam));
        return ret;
    }


    /**
     * 添加分账规则调用此接口添加分账规则。添加的分账规则需要审核后才可使用。
     */
    public static JSONObject addProfitSharingRule(AddProfitSharingRuleParam addProfitSharingRuleParam, String type, String pwd) throws IOException {
        setTradeWayFeilds(addProfitSharingRuleParam, addProfitSharingRuleParam.getMerWalletId(), type, pwd, "");
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.addProfitSharingRule", initBizContent(addProfitSharingRuleParam));
    }

    /**
     * 查询分账规则
     */
    public static JSONObject queryProfitSharingRule(QueryProfitSharingRuleParam queryProfitSharingRuleParam, String type, String pwd) throws IOException {
        setTradeWayFeilds(queryProfitSharingRuleParam, queryProfitSharingRuleParam.getPlatformWalletId(), type, pwd, "");
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102QueryRpcService.queryProfitSharingRule", initBizContent(queryProfitSharingRuleParam));
    }

    /**
     * 查询注册登记信息
     * 主要应用于 B 端 H5/PC 开户后，使用登记手机号或注册号查询注册登记信息。
     *
     * @param h5Parm
     * @return
     * @throws IOException
     */
    public static JSONObject queryRegInfo(H5Parm h5Parm) throws IOException {
        String msg = JSON.toJSONString(h5Parm);
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wextbc.EeepmsRegQueryRPCRpcService.queryRegInfo", msg);
    }

    /**
     * 查询结算登记
     */
    public static JSONObject querySettRegDetail(QuerySettRegDetailParam querySettRegDetailParam) throws IOException {
//        String msg = JSON.toJSONString(querySettRegDetailParam);
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102QueryRpcService.querySettRegDetail", initBizContent(querySettRegDetailParam));
    }

    /**
     * 开户C端
     * C 端电子账户的开户接口。经过渠道验证后开设实名制的 C 端账户。
     */
    public static JSONObject openAcct(OpenAcctParm openAcctParm) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.openAcct", initBizContent(openAcctParm));
    }

    /**
     * 激活账户C端
     */
    public static JSONObject activeAcct(ActiveAcctParam activeAcctParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.activeAcct", initBizContent(activeAcctParam));
    }

    /**
     * 发送短信验证码
     */
    public static JSONObject sendSmsAuthCode(SendSmsAuthCodeParam sendSmsAuthCodeParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.sendSmsAuthCode", initBizContent(sendSmsAuthCodeParam));
    }

    /**
     * 验证短信验证码
     */
    public static JSONObject validSmsAuthCode(ValidSmsAuthCodeParam validSmsAuthCodeParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.validSmsAuthCode", initBizContent(validSmsAuthCodeParam));
    }


    /**
     * 修改密码用于修改 B 端或者 C 端电子账户的支付密码
     */
    public static JSONObject modifyPwd(ModifyPwdParam modifyPwdParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.modifyPwd", initBizContent(modifyPwdParam));
    }

    /**
     * 账户绑定/解绑/设置默认银行卡
     */
    public static JSONObject acctBindBankCard(AcctBindBankCardParam acctBindBankCardParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.acctBindBankCard", initBizContent(acctBindBankCardParam));
    }

    /**
     * 查询账户银行卡
     */
    public static JSONObject queryBindBankCard(QueryBindBankCardParam queryBindBankCardParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102QueryRpcService.queryBindBankCard", initBizContent(queryBindBankCardParam));
    }

    /**
     * 银行卡签约申请
     * 持卡人进行银行卡签约申请接口。申请成功后，持卡人会收到由银行发送的短信验证
     * 码，在进行签约确认时需要将短信验证码上送。
     * 只有 C 端账户可以进行银行卡签约，完成签约后账户可以进行充值操作。
     */
    public static JSONObject bankAcctSignApply(BankAcctSignApplyParam bankAcctSignApplyParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.bankAcctSignApply", initBizContent(bankAcctSignApplyParam));
    }

    /**
     * 银行卡签约确认
     * 持卡人进行银行卡签约确认接口。进行签约确认时需要原签约申请流水号和短信验证
     * 码。
     */
    public static JSONObject bankAcctSignConfirm(BankAcctSignConfirmParam bankAcctSignConfirmParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.bankAcctSignConfirm", initBizContent(bankAcctSignConfirmParam));
    }

    /**
     * 银行卡解约
     * 已经签约成功的银行卡，调用此接口进行解约。
     */
    public static JSONObject bankAcctSignCancel(BankAcctSignCancelParam bankAcctSignCancelParam) throws IOException {
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.bankAcctSignCancel", initBizContent(bankAcctSignCancelParam));
    }


    /**
     * 代扣充值  暂时不可用
     * 从用户已签约的银行卡中扣款，充值到 C 端电子账户。
     */
    public static JSONObject cpsDsRecharge(CpsDsRechargeParam cpsDsRechargeParam, String type, String pwd) throws IOException {
        setTradeWayFeilds(cpsDsRechargeParam, cpsDsRechargeParam.getWalletId(), type, pwd, "");
        cpsDsRechargeParam.setTradeWayCode("c_pass");
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.cpsDsRecharge", initBizContent(cpsDsRechargeParam));
    }

    /**
     * 异步提现
     */
    public static JSONObject asyncWithdraw(AsyncWithdrawParam asyncWithdrawParam, String type, String pwd, String plugRandKey) throws IOException {
        setTradeWayFeilds(asyncWithdrawParam, asyncWithdrawParam.getWalletId(), type, pwd, plugRandKey);
        return GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.asyncWithdraw", initBizContent(asyncWithdrawParam));
    }


    /**
     * 验证域方法
     *
     * @param param
     * @param walletId
     * @param type
     * @throws IOException
     */
    private static void setTradeWayFeilds(BaseParam param, String walletId, String type, String encryptPwd, String plugRandKey) throws IOException {
        if (StringUtils.isEmpty(plugRandKey)) {
            plugRandKey = getPlugRandKey();
        }
        Map<String, String> tradeWayFields = new HashMap<>();
        if ("PWD".equals(type)) {
            // 支付密码验证，实际应用中，由密码控件加密支付密码，这里用固定的加密结果进行演示
            param.setTradeWayCode("b_pass");
            tradeWayFields.put("encryptType", ENCRYPT_TYPE);
            tradeWayFields.put("encryptPwd", encryptPwd);
            tradeWayFields.put("plugRandomKey", plugRandKey);
        } else if ("CERT".equals(type)) {
            // 交易证书签名
            param.setTradeWayCode("b_certificate");
            String signSource = walletId + "," + plugRandKey;
            tradeWayFields.put("plugRandomKey", plugRandKey);
            tradeWayFields.put("certSign", CryptoUtils.sign("SHA256withRSA", signSource, MCT_WALLET_TRANS_CERT, "UTF-8"));
        }
        param.setTradeWayFeilds(JSON.toJSONString(tradeWayFields));
    }

    /**
     * 获取控件随机因子，支付密码、交易证书中用到控件随机因子都应该通过接口获取，DEMO中支付密码中用到控件随机因子是固定的仅仅为了方便演示
     */
    public static String getPlugRandKey() throws IOException {
        if (plugRandKeyQueue.isEmpty()) {
            GetPlugRandomKeyParam param = new GetPlugRandomKeyParam();
            param.setApplyCount(PLUG_RAND_KEY_QUEUE_SIZE);
            JSONObject ret = GneteGatewayRequestUtils.callGneteGateway("gnete.wallbc.WallbcOpenapi102TransRpcService.getPlugRandomKey", initBizContent(param));
            GetPlugRandomKeyResult result = parseResult(ret.getString("response"), GetPlugRandomKeyResult.class);
            List<String> plugRandKeys = result.getList().stream().map(GetPlugRandomKeyResult.Row::getPlugRandomKey).collect(Collectors.toList());
            plugRandKeyQueue.addAll(plugRandKeys);
        }
        return plugRandKeyQueue.poll();
    }

    private static String initBizContent(BaseParam param) {
        WalletRequest request = new WalletRequest();
        request.setIssrId(ISSR_ID);
        request.setReqSn(UUID.randomUUID().toString().replace("-", ""));
        request.setSndDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        request.setMsgBody(JSON.toJSONString(param));
        return JSON.toJSONString(request);
    }

    private static <T extends BaseResult> T parseResult(String responseBody, Class<T> cls) {
        WalletResponse response = JSON.parseObject(responseBody, WalletResponse.class);
        return JSON.parseObject(response.getMsgBody(), cls);
    }


    /**
     * 通用返回法
     *
     * @param
     * @throws Exception
     */
    public static ResultParam result(JSONObject param) {
        ResultParam resultParam = new ResultParam();
        param = param.getJSONObject("response");
        resultParam.setRspCode(param.getString("rspCode"));
        resultParam.setRspResult(param.getString("rspResult"));
        if (CODE.equals(resultParam.getRspCode())) {
            resultParam.setMsgBody(JSONObject.parseObject(param.getString("msgBody")));
        }
        return resultParam;
    }

    public static void main(String[] args) throws Exception {
        String orderNo = System.currentTimeMillis() + "";

//        PageQueryHzfOriTransParam pageQueryHzfOriTransParam = new PageQueryHzfOriTransParam();
//        pageQueryHzfOriTransParam.setPlatformWalletId("2061093300250533560");
//        pageQueryHzfOriTransParam.setStartSettDate("20220301");
//        pageQueryHzfOriTransParam.setEndSettDate("20220321");
//        JSONObject pageQueryHzfOriTransParamMsg = pageQueryHzfOriTrans(pageQueryHzfOriTransParam);
//        System.out.println("分页查询好支付原交易----------" + pageQueryHzfOriTransParamMsg);
        //确认结算
//        PaySettConfirmParam paySettConfirmParam = new PaySettConfirmParam();
//        paySettConfirmParam.setMctOrderNo(System.currentTimeMillis() + "");
//        paySettConfirmParam.setRefNo("9999999");
//        paySettConfirmParam.setSettDate("20220312");
//        paySettConfirmParam.setPlatformWalletId(ZONG_DAIAN_WALLET_ID);
//        paySettConfirmParam.setSettAmt("1");
//        paySettConfirmParam.setIsFinished("N");
//        paySettConfirmParam.setBizType("030002");
//        paySettConfirmParam.setProfitSharingRuleId("300354");
////
//        List<ProfitSharingList> list = new ArrayList<>();
//        ProfitSharingList profitSharingList = new ProfitSharingList();
//        profitSharingList.setProfitSharingAmt("1");
//        profitSharingList.setProfitSharingWalletId("2061093300250533560");
//        profitSharingList.setProfitSharingDesc("充人数");
//        list.add(profitSharingList);
//        paySettConfirmParam.setProfitSharingList(list);
//        JSONObject paySettConfirmParamMsg = paySettConfirm(paySettConfirmParam);
//        System.out.println("确认结算----------" + paySettConfirmParamMsg);


//        getPlugRandKey();

        //查询分账规则
//        QueryProfitSharingRuleParam queryProfitSharingRuleParam = new QueryProfitSharingRuleParam();
//        queryProfitSharingRuleParam.setPageNo("1");
//        queryProfitSharingRuleParam.setPageSize("50");
//        queryProfitSharingRuleParam.setPlatformWalletId("2061747300242180169");
//        queryProfitSharingRuleParam.setId("300349");
//        JSONObject queryProfitSharingRuleMsg = queryProfitSharingRule(queryProfitSharingRuleParam, "CERT");
//        System.out.println("查询分账规则----------" + queryProfitSharingRuleMsg);


        //添加关联方
//        AddProfitSharingMerRelParam addProfitSharingMerRelParam = new AddProfitSharingMerRelParam();
//        addProfitSharingMerRelParam.setMerWalletId("2061747300242180169");
//        addProfitSharingMerRelParam.setMerName("联调接口测试企业");
//        addProfitSharingMerRelParam.setBizType("030002");
//        List<AddProfitSharingMerRelListParam> list = new ArrayList<>();
//        AddProfitSharingMerRelListParam addProfitSharingMerRelListParam = new AddProfitSharingMerRelListParam();
//        addProfitSharingMerRelListParam.setRelMerWalletId("2061093300250533560");
//        addProfitSharingMerRelListParam.setRelMerName("猫志斌测试");
//        list.add(addProfitSharingMerRelListParam);
//        addProfitSharingMerRelParam.setList(list);
//        JSONObject addProfitSharingMerRelParamMsg = addProfitSharingMerRel(addProfitSharingMerRelParam, "CERT");
//        System.out.println("添加关联方----------" + addProfitSharingMerRelParamMsg);
        //查询关联分账方
//        QueryProfitSharingMerRelParam queryProfitSharingMerRelParam = new QueryProfitSharingMerRelParam();
//        queryProfitSharingMerRelParam.setPlatformWalletId("2061747300242180169");
//        queryProfitSharingMerRelParam.setRelMerWalletId("2061093300250533560");
//        JSONObject queryProfitSharingMerRelParamMsg = queryProfitSharingMerRel(queryProfitSharingMerRelParam, "CERT");
//        System.out.println("查询关联分账方----------" + queryProfitSharingMerRelParamMsg);
        //异步提现
//        AsyncWithdrawParam asyncWithdrawParam = new AsyncWithdrawParam();
//        asyncWithdrawParam.setMctOrderNo(System.currentTimeMillis() + "");
//        asyncWithdrawParam.setWalletId("2060210200250532929");
//        asyncWithdrawParam.setAmount("1");
////        asyncWithdrawParam.setBankAcctNo("6217568000062153158");
//        asyncWithdrawParam.setWithdrawType("T0");
//        JSONObject asyncWithdrawParamMsg = asyncWithdraw(asyncWithdrawParam, "PWD");
//        System.out.println("异步提现----------" + asyncWithdrawParamMsg);


        //代扣充值
//        CpsDsRechargeParam cpsDsRechargeParam = new CpsDsRechargeParam();
//        cpsDsRechargeParam.setMctOrderNo(System.currentTimeMillis() + "");
//        cpsDsRechargeParam.setWalletId("2060210200250532929");
//        cpsDsRechargeParam.setAmount("100");
//        cpsDsRechargeParam.setBankAcctNo("6217568000062153158");
////        cpsDsRechargeParam.setTradeWayCode("c_pass");
////        cpsDsRechargeParam.setEncryptType(ENCRYPT_TYPE);
////        cpsDsRechargeParam.setEncryptPwd(ENCRYPTED_PWD);
////        cpsDsRechargeParam.setPlugRandomKey(PLUG_RAND_KEY);
//        JSONObject cpsDsRechargeParamMsg = cpsDsRecharge(cpsDsRechargeParam,"PWD");
//        System.out.println("代扣充值----------" + cpsDsRechargeParamMsg);

        //银行卡解约
//        BankAcctSignCancelParam bankAcctSignCancelParam = new BankAcctSignCancelParam();
//        bankAcctSignCancelParam.setWalletId("2060210200250532929");
//        bankAcctSignCancelParam.setAccountNo("6217568000062153158");
//        bankAcctSignCancelParam.setTradeWayCode("c_pass");
//        bankAcctSignCancelParam.setEncryptType(ENCRYPT_TYPE);
//        bankAcctSignCancelParam.setEncryptPwd(ENCRYPTED_PWD);
//        bankAcctSignCancelParam.setPlugRandomKey(PLUG_RAND_KEY);
//        JSONObject bankAcctSignCancelParamMsg = bankAcctSignCancel(bankAcctSignCancelParam);
//        System.out.println("银行卡解约----------" + bankAcctSignCancelParamMsg);


        //银行卡签约申请
//        BankAcctSignApplyParam bankAcctSignApplyParam = new BankAcctSignApplyParam();
//        bankAcctSignApplyParam.setWalletId("2060210200250532929");
//        bankAcctSignApplyParam.setAccountType("00");
//        bankAcctSignApplyParam.setIdType("0");
//        bankAcctSignApplyParam.setIdNo("150403199312210520");
//        bankAcctSignApplyParam.setTel("15168301233");
//        bankAcctSignApplyParam.setAccountNo("6217568000062153158");
//        bankAcctSignApplyParam.setAccountName("刘佳新");
//        JSONObject bankAcctSignApplyParamMsg = bankAcctSignApply(bankAcctSignApplyParam);
//        System.out.println("银行卡签约申请----------" + bankAcctSignApplyParamMsg);


        //银行卡签约确认
//        BankAcctSignConfirmParam bankAcctSignConfirmParam = new BankAcctSignConfirmParam();
//        bankAcctSignConfirmParam.setWalletId("2060210200250532929");
//        bankAcctSignConfirmParam.setVerifyCode("111111");
//        bankAcctSignConfirmParam.setOriReqSn("202203120001033865737988460638");
//        JSONObject bankAcctSignConfirmParamMsg = bankAcctSignConfirm(bankAcctSignConfirmParam);
//        System.out.println("银行卡签约确认----------" + bankAcctSignConfirmParamMsg);


        //查询银行卡
//        QueryBindBankCardParam queryBindBankCardParam = new QueryBindBankCardParam();
//        queryBindBankCardParam.setWalletId("2060210200250532929");
//        JSONObject queryBindBankCardParamMsg = queryBindBankCard(queryBindBankCardParam);
//        System.out.println("查询银行卡----------" + queryBindBankCardParamMsg);
        //手续费查询
//        QueryFeeParam queryFeeParam = new QueryFeeParam();
//        queryFeeParam.setWalletId("2060210200250532929");
//        queryFeeParam.setTransAmt("100000");
//        queryFeeParam.setTransType("wallbc020006");
//        JSONObject queryFeeParamParamMsg = queryFee(queryFeeParam);
//        System.out.println("手续费查询----------" + queryFeeParamParamMsg);

        //手续费费率查询
//        QueryFeeRateParam queryFeeRateParam = new QueryFeeRateParam();
//        queryFeeRateParam.setWalletId("2060210200250532929");
//        queryFeeRateParam.setTransType("wallbc020006");
//        JSONObject queryFeeRateParamMsg = queryFeeRate(queryFeeRateParam);
//        System.out.println("手续费费率查询----------" + queryFeeRateParamMsg);
        //账户绑定/解绑/设置默认银行卡
//        AcctBindBankCardParam acctBindBankCardParam = new AcctBindBankCardParam();
//        acctBindBankCardParam.setWalletId("2060210200250532929");
//        acctBindBankCardParam.setOprtType("2");
//        acctBindBankCardParam.setBankAcctType("1");
//        acctBindBankCardParam.setIdCard("150403199312210520");
//        acctBindBankCardParam.setBankAcctNo("112222222222");
//        acctBindBankCardParam.setMobileNo("15168301233");
//        acctBindBankCardParam.setBankAcctName("刘佳新");
//        acctBindBankCardParam.setBankNo("121");
//        acctBindBankCardParam.setTradeWayCode("c_pass");
//        acctBindBankCardParam.setEncryptType(ENCRYPT_TYPE);
//        acctBindBankCardParam.setEncryptPwd(ENCRYPTED_PWD);
//        acctBindBankCardParam.setPlugRandomKey(PLUG_RAND_KEY);
//        JSONObject acctBindBankCardParamMsg = acctBindBankCard(acctBindBankCardParam);
//        System.out.println("//账户绑定/解绑/设置默认银行卡----------" + acctBindBankCardParamMsg);
        //查询交易明细
//        QueryTransListParam queryTransListParam = new QueryTransListParam();
//        queryTransListParam.setPageNo("1");
//        queryTransListParam.setPageSize("2");
//        queryTransListParam.setWalletId("2061747300242180169");
//        queryTransListParam.setStartDate("20220301");
//        queryTransListParam.setEndDate("20220312");
//        queryTransListParam.setIsNeedPwd("0");
//        JSONObject queryTransListParamMsg = queryTransList(queryTransListParam);
//        System.out.println("查询交易明细----------" + queryTransListParamMsg);
        //账单查询
//        QueryBillInfoParam queryBillInfoParam = new QueryBillInfoParam();
//        queryBillInfoParam.setWalletId("2061747300242180169");
//        queryBillInfoParam.setStartDate("2022-03-01");
//        queryBillInfoParam.setEndDate("2022-03-12");
//        queryBillInfoParam.setPageType("1");
//        JSONObject queryBillInfoParamMsg = queryBillInfo(queryBillInfoParam);
//        System.out.println("账单查询----------" + queryBillInfoParamMsg);
//        //获取凭证接口
//        H5Parm h5Parm = new H5Parm();
//        h5Parm.setJumpType("3");
//        h5Parm.setWalletId("2060210200250532929");
//        h5Parm.setExtUserId(System.currentTimeMillis() + "");
//        h5Parm.setCallbackUrl("www.baidu.com");
//        h5Parm.setNotifyUrl("www.baidu.com");
//        JSONObject H5jsonObject = h5(h5Parm);
//        System.out.println("获取凭证接口----------" + H5jsonObject);
        //验证凭据接口
//        ValidateTicketParam validateTicketParam = new ValidateTicketParam();
//        validateTicketParam.setTicket("ac0da8ad8add48f59625e5d9aa96a565");
//        JSONObject validateTicketParamjsonObject = validateTicket(validateTicketParam);
//        System.out.println("验证凭据接口----------" + validateTicketParamjsonObject);
        //外包查询店
        QueryWalletIdParam queryWalletIdParam = new QueryWalletIdParam();
        queryWalletIdParam.setExtUserId("3123132");
        JSONObject queryWalletIdParamMsg = queryWalletId(queryWalletIdParam);
        System.out.println("外部用户查询电子账户 ID----------" + queryWalletIdParamMsg);
        //查询注册登记信息
//        QueryRegInfoParam queryRegInfoParam = new QueryRegInfoParam();
//        queryRegInfoParam.setMobileNo("13093783517");
//        JSONObject queryRegInfoMsg = queryRegInfo(queryRegInfoParam);
//        System.out.println("查询注册登记信息----------" + queryRegInfoMsg);
        //c端接口开户
//        OpenAcctParm openAcctParm = new OpenAcctParm();
//        openAcctParm.setUserName("刘佳新");
//        openAcctParm.setMobileNo("15168301233");
//        openAcctParm.setIdCard("150403199312210520");
//        openAcctParm.setAuthType("2");
//        openAcctParm.setIsActive("0");
//        openAcctParm.setTradeWayCode("c_pass");
//        openAcctParm.setEncryptType(ENCRYPT_TYPE);
//        openAcctParm.setEncryptPwd(ENCRYPTED_PWD);
//        openAcctParm.setPlugRandomKey(PLUG_RAND_KEY);
//        JSONObject openAcctParmMsg = openAcct(openAcctParm);
//        System.out.println("c端接口开户----------" + openAcctParmMsg);
        //激活c端账户
//        ActiveAcctParam activeAcctParam = new ActiveAcctParam();
//        activeAcctParam.setWalletId("2060210200250532929");
//        activeAcctParam.setTradeWayCode("c_pass");
//        activeAcctParam.setEncryptType(ENCRYPT_TYPE);
//        activeAcctParam.setEncryptPwd(ENCRYPTED_PWD);
//        activeAcctParam.setPlugRandomKey(PLUG_RAND_KEY);
//        JSONObject activeAcctParamMsg = activeAcct(activeAcctParam);
//        System.out.println("激活c端账户----------" + activeAcctParamMsg);
        //短信验证码
//        SendSmsAuthCodeParam sendSmsAuthCodeParam = new SendSmsAuthCodeParam();
//        sendSmsAuthCodeParam.setMobileNo("13093783517");
//        sendSmsAuthCodeParam.setSmsTmpltCode("COMMON");
//        sendSmsAuthCodeParam.setSmsBizType("2");
//        JSONObject sendSmsAuthCodeParamMsg = sendSmsAuthCode(sendSmsAuthCodeParam);
//        System.out.println("短信验证码----------" + sendSmsAuthCodeParamMsg);
        //验证短信验证码
//        ValidSmsAuthCodeParam validSmsAuthCodeParam = new ValidSmsAuthCodeParam();
//        validSmsAuthCodeParam.setMobileNo("13093783517");
//        validSmsAuthCodeParam.setSmsAuthCode("111111");
//        JSONObject validSmsAuthCodeParamMsg = validSmsAuthCode(validSmsAuthCodeParam);
//        System.out.println("验证短信验证码----------" + validSmsAuthCodeParamMsg);
        //修改密码
//        ModifyPwdParam modifyPwdParam = new ModifyPwdParam();
//        modifyPwdParam.setWalletId("2060270000249550994");
//        modifyPwdParam.setEncryptNewPwd(ENCRYPTED_PWD);
//        modifyPwdParam.setEncryptOrigPwd(ENCRYPTED_PWD);
//        modifyPwdParam.setEncryptType(ENCRYPT_TYPE);
//        modifyPwdParam.setPlugRandomKey(PLUG_RAND_KEY);
//        JSONObject modifyPwdParamMsg = modifyPwd(modifyPwdParam);
//        System.out.println("修改密码----------" + modifyPwdParamMsg);
        //重置支付密码
//        ResetBtypeAcctPwdParam resetBtypeAcctPwdParam = new ResetBtypeAcctPwdParam();
//        resetBtypeAcctPwdParam.setWalletId("2060270000249550994");
//        resetBtypeAcctPwdParam.setEncryptType(ENCRYPT_TYPE);
//        resetBtypeAcctPwdParam.setEncryptNewPwd(ENCRYPTED_PWD);
//        resetBtypeAcctPwdParam.setPlugRandomKey(PLUG_RAND_KEY);
//        resetBtypeAcctPwdParam.setLegalName("吴军");
//        resetBtypeAcctPwdParam.setLegalIdCard("332502199409242873");
//        resetBtypeAcctPwdParam.setLegalPhoneNum("13587130141");
//        resetBtypeAcctPwdParam.setLegalSmsAuthCode("111111");
//        JSONObject resetBtypeAcctPwdParamMsg = resetBtypeAcctPwd(resetBtypeAcctPwdParam);
//        System.out.println("重置支付密码----------" + resetBtypeAcctPwdParamMsg);
//        //查询账户关联信息
//        QueryAcctRelatedInfoParam queryAcctRelatedInfoParam = new QueryAcctRelatedInfoParam();
//        queryAcctRelatedInfoParam.setWalletId("2060270000249550994");
//        queryAcctRelatedInfoParam.setMobileNo("13587130141");
//        JSONObject queryAcctRelatedInfoParamMsg = queryAcctRelatedInfo(queryAcctRelatedInfoParam);
//        System.out.println("查询账户关联信息----------" + queryAcctRelatedInfoParamMsg);
        //查询钱包余额
//        QueryAcctBalParam queryAcctBalParam = new QueryAcctBalParam();
//        queryAcctBalParam.setWalletId("2061747300242180169");
//        queryAcctBalParam.setIsNeedPwd(0);
//        JSONObject jsonObject = queryAcctBal(queryAcctBalParam);
//        System.out.println("查询钱包余额----------" + jsonObject);
        //钱包转账
//        TransferParam transferParam = new TransferParam();
//        transferParam.setFromWalletId("2061747300242180169");
//        transferParam.setIntoWalletId("2060210200250532929");
//        transferParam.setTransferAmt("100");
//        transferParam.setMctOrderNo(orderNo);
//        transferParam.setRemark("测试");
//        JSONObject jsonObject = transfer(transferParam, "CERT");
//        System.out.println("钱包转账----------" + jsonObject);
//        //查询单个交易结果
//        QueryTransResultParam queryTransResultParam = new QueryTransResultParam();
//        queryTransResultParam.setWalletId("2060210200250532929");
//        queryTransResultParam.setMctOrderNo("1647087314701");
//        JSONObject queryTransResultMsg = queryTransResult(queryTransResultParam);
//        System.out.println("查询单个交易结果----------" + queryTransResultMsg);
        //查询账户信息
//        QueryAcctInfoParam queryAcctInfo = new QueryAcctInfoParam();
//        queryAcctInfo.setWalletId("2061747300242180169");
//        JSONObject queryAcctInfotMsg = queryAcctInfo(queryAcctInfo);
//        System.out.println("查询账户信息----------" + queryAcctInfotMsg);
//        钱包批量转账
//        BatchTransferParam batchTransferParam = new BatchTransferParam();
//        batchTransferParam.setMctOrderNo(orderNo);
//        batchTransferParam.setFromWalletId("2061747300242180169");
//        batchTransferParam.setTotalAmt("1");
//        batchTransferParam.setTotalCount("1");
//        List<BatchTransferListParam> batchTransferListParams = new ArrayList<>();
//        BatchTransferListParam batchTransferListParam = new BatchTransferListParam();
//        batchTransferListParam.setSn(System.currentTimeMillis() + "");
//        batchTransferListParam.setIntoWalletId("2060699200218102156");
//        batchTransferListParam.setAmount("1");
//        batchTransferListParam.setBizType("010005");
//        batchTransferListParam.setIntoWalletName("银联测试");
//        batchTransferListParams.add(batchTransferListParam);
//        batchTransferParam.setList(batchTransferListParams);
//        JSONObject batchTransferParamMsg = batchTransfer(batchTransferParam, "CERT");
//        System.out.println("钱包批量转账----------" + batchTransferParamMsg);
        //批量转账查询
//        QueryBatchTransResultParam queryTransResultParam = new QueryBatchTransResultParam();
//        queryTransResultParam.setOriMctOrderNo("1646806008193");
//        queryTransResultParam.setOperType("3");
//        JSONObject queryTransResultParamMsg = queryBatchTransResult(queryTransResultParam);
//        System.out.println("查询批量交易结果----------" + queryTransResultParamMsg);
//        //添加分账规则
//        AddProfitSharingRuleParam addProfitSharingRuleParam = new AddProfitSharingRuleParam();
//        addProfitSharingRuleParam.setMerWalletId(ZONG_DAIAN_WALLET_ID);
//        addProfitSharingRuleParam.setMerName("联调接口测试企业");
//        addProfitSharingRuleParam.setBizType("030002");
//        addProfitSharingRuleParam.setIsInstr(1);
//        addProfitSharingRuleParam.setSettRuleType("01");
//        List<SettRuleListParam> settRuleListParamList = new ArrayList<>();
//        SettRuleListParam settRuleListParam = new SettRuleListParam();
//        settRuleListParam.setProfitSharingWalletId("2061093300250533560");
//        settRuleListParam.setProfitSharingWalletName("猫志斌测试");
//        settRuleListParam.setSettRate("10000");
//        settRuleListParam.setRateMinSettAmt(0);
//        settRuleListParam.setRateMaxSettAmt(999999999);
//        settRuleListParamList.add(settRuleListParam);
//        addProfitSharingRuleParam.setSettRuleList(settRuleListParamList);
//        List<FileListParam> fileListParamList = new ArrayList<>();
//        FileListParam fileListParam = new FileListParam();
//        fileListParam.setFileId("4426d0b853774dc8877d90a8883d759f");
//        fileListParamList.add(fileListParam);
//        addProfitSharingRuleParam.setFileList(fileListParamList);
//        JSONObject addProfitSharingRuleMsg = addProfitSharingRule(addProfitSharingRuleParam, "CERT");
//        System.out.println("分账规则----------" + addProfitSharingRuleMsg);
    }

}
