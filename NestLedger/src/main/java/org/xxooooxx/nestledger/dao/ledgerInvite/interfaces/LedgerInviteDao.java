package org.xxooooxx.nestledger.dao.ledgerInvite.interfaces;

import org.xxooooxx.nestledger.to.LedgerInviteDB;
import org.xxooooxx.nestledger.vo.ledgerInvite.request.LedgerInviteCreateRequestData;
import org.xxooooxx.nestledger.vo.ledgerInvite.request.LedgerInviteGetRequestData;

import java.util.List;

public interface LedgerInviteDao {
    LedgerInviteDB createLedgerInvite(LedgerInviteCreateRequestData data);
    List<LedgerInviteDB> getLedgerInvite(LedgerInviteGetRequestData data);
}
