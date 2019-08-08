/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.pack.alpha.server.api;

import java.util.List;
import java.util.Map;
import org.apache.servicecomb.pack.alpha.core.api.APIv1;
import org.apache.servicecomb.pack.alpha.core.fsm.repository.model.GlobalTransaction;
import org.apache.servicecomb.pack.alpha.core.fsm.repository.model.PagingGlobalTransactions;
import org.apache.servicecomb.pack.alpha.core.metrics.AlphaMetrics;
import org.apache.servicecomb.pack.alpha.fsm.repository.TransactionRepository;
import org.apache.servicecomb.pack.alpha.server.metrics.AlphaMetricsEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class APIv1Impl implements APIv1 {

  @Autowired
  AlphaMetricsEndpoint alphaMetricsEndpoint;

  @Autowired(required = false)
  TransactionRepository transactionRepository;

  public AlphaMetrics getMetrics() {
    AlphaMetrics alphaMetrics = new AlphaMetrics();
    alphaMetrics.setMetrics(alphaMetricsEndpoint.getMetrics());
    alphaMetrics.setNodeType(alphaMetricsEndpoint.getNodeType());
    return alphaMetrics;
  }

  public GlobalTransaction getTransactionByGlobalTxId(String globalTxId)
      throws Exception {
    GlobalTransaction globalTransaction = transactionRepository
        .getGlobalTransactionByGlobalTxId(globalTxId);
    return globalTransaction;
  }

  public PagingGlobalTransactions getTransactions(int page, int size)
      throws Exception {
    return getTransactions(null, page, size);
  }

  public PagingGlobalTransactions getTransactions(String state, int page, int size)
      throws Exception {
    PagingGlobalTransactions pagingGlobalTransactions = transactionRepository
        .getGlobalTransactions(state, page, size);
    return pagingGlobalTransactions;
  }

  public Map<String, Long> getTransactionStatistics() {
    return transactionRepository.getTransactionStatistics();
  }

  public List<GlobalTransaction> getSlowTransactions() {
    return transactionRepository.getSlowGlobalTransactionsTopN(10);
  }
}
