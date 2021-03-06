/*
 * Copyright 2013 bits of proof zrt.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bitsofproof.supernode.core;

import java.util.List;

import com.bitsofproof.supernode.api.BloomFilter;
import com.bitsofproof.supernode.api.BloomFilter.UpdateMode;
import com.bitsofproof.supernode.api.ValidationException;
import com.bitsofproof.supernode.model.Blk;
import com.bitsofproof.supernode.model.Tx;

public interface BlockStore extends ColorStore
{
	public interface CacheContextRunnable
	{
		public void run (TxOutCache cache) throws ValidationException;
	}

	public interface TransactionProcessor
	{
		public void process (Tx transaction);
	}

	public void cache (Chain chain, int cacheSize) throws ValidationException;

	public ValidationException runInCacheContext (CacheContextRunnable runnable);

	public void addTrunkListener (TrunkListener l);

	public long getPeriodLength (String previousHash, int reviewPeriod);

	public List<String> getLocator ();

	public void storeBlock (Blk b) throws ValidationException;

	public String getHeadHash ();

	public void resetStore (Chain chain) throws ValidationException;

	public Blk getBlock (String hash) throws ValidationException;

	public Tx getTransaction (String hash) throws ValidationException;

	public boolean isStoredBlock (String hash);

	public boolean validateTransaction (Tx tx, TxOutCache resolvedInputs) throws ValidationException;

	public void resolveTransactionInputs (Tx tx, TxOutCache resolvedInputs) throws ValidationException;

	public long getChainHeight ();

	public boolean isEmpty ();

	public List<String> getInventory (List<String> locator, String last, int limit);

	public void scan (BloomFilter filter, TransactionProcessor processor) throws ValidationException;

	public void filterTransactions (List<byte[]> data, UpdateMode update, long after, TransactionProcessor processor) throws ValidationException;
}