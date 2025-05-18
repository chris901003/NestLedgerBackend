package org.xxooooxx.nestledger.service.ledgerInvite.interfaces;

import org.xxooooxx.nestledger.vo.ledgerInvite.request.LedgerInviteCreateRequestData;
import org.xxooooxx.nestledger.vo.ledgerInvite.request.LedgerInviteGetRequestData;
import org.xxooooxx.nestledger.vo.ledgerInvite.response.LedgerInviteGetResponseData;

import java.util.List;

public interface LedgerInviteService {
    LedgerInviteGetResponseData createLedgerInvite(LedgerInviteCreateRequestData data);
    List<LedgerInviteGetResponseData> getLedgerInvite(LedgerInviteGetRequestData data);
}
