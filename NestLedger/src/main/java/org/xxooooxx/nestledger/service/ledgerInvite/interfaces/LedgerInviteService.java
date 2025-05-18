package org.xxooooxx.nestledger.service.ledgerInvite.interfaces;

import org.xxooooxx.nestledger.vo.ledgerInvite.request.LedgerInviteCreateRequestData;
import org.xxooooxx.nestledger.vo.ledgerInvite.response.LedgerInviteGetResponseData;

public interface LedgerInviteService {
    LedgerInviteGetResponseData createLedgerInvite(LedgerInviteCreateRequestData data);
}
