package org.xxooooxx.nestledger.dao.ledgerInvite.interfaces;

import org.xxooooxx.nestledger.to.LedgerInviteDB;
import org.xxooooxx.nestledger.vo.ledgerInvite.request.LedgerInviteCreateRequestData;

public interface LedgerInviteDao {
    LedgerInviteDB createLedgerInvite(LedgerInviteCreateRequestData data);
}
