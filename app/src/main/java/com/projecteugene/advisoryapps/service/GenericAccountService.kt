package com.projecteugene.advisoryapps.service

import android.accounts.*
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.os.Handler

/**
 * Created by Eugene Low
 */
@Suppress("unused")
class GenericAccountService : Service() {
    private var mAuthenticator: Authenticator? = null

    var handler = Handler()

    override fun onCreate() {
        Log.i(TAG, "Service created")
        mAuthenticator = Authenticator(this)
    }

    override fun onDestroy() {
        Log.i(TAG, "Service destroyed")
    }

    override fun onBind(intent: Intent): IBinder? {
        return mAuthenticator?.iBinder
    }

    inner class Authenticator(context: Context) : AbstractAccountAuthenticator(context) {

        override fun editProperties(accountAuthenticatorResponse: AccountAuthenticatorResponse,
                                    s: String): Bundle {
            throw UnsupportedOperationException()
        }

        @Throws(NetworkErrorException::class)
        override fun addAccount(accountAuthenticatorResponse: AccountAuthenticatorResponse,
                                accountType: String, authTokenType: String?,
                                requiredFeatures: Array<String>?, options: Bundle?): Bundle? {
            throw UnsupportedOperationException()
        }

        @Throws(NetworkErrorException::class)
        override fun confirmCredentials(accountAuthenticatorResponse: AccountAuthenticatorResponse,
                                        account: Account, bundle: Bundle): Bundle? {
            throw UnsupportedOperationException()
        }

        @Throws(NetworkErrorException::class)
        override fun getAuthToken(accountAuthenticatorResponse: AccountAuthenticatorResponse,
                                  account: Account, s: String, bundle: Bundle): Bundle {
            throw UnsupportedOperationException()
        }

        override fun getAuthTokenLabel(s: String): String {
            throw UnsupportedOperationException()
        }

        @Throws(NetworkErrorException::class)
        override fun updateCredentials(accountAuthenticatorResponse: AccountAuthenticatorResponse,
                                       account: Account, s: String, bundle: Bundle): Bundle {
            throw UnsupportedOperationException()
        }

        @Throws(NetworkErrorException::class)
        override fun hasFeatures(accountAuthenticatorResponse: AccountAuthenticatorResponse,
                                 account: Account, strings: Array<String>): Bundle {
            throw UnsupportedOperationException()
        }
    }

    companion object {
        private val TAG = GenericAccountService::class.java.simpleName
        private const val ACCOUNT_NAME = "Account"

        /**
         * Obtain a handle to the [android.accounts.Account] used for sync in this application.
         *
         *
         * It is important that the accountType specified here matches the value in your sync adapter
         * configuration XML file for android.accounts.AccountAuthenticator (often saved in
         * res/xml/syncadapter.xml). If this is not set correctly, you'll receive an error indicating
         * that "caller uid XXXXX is different than the authenticator's uid".
         *
         * @param accountType AccountType defined in the configuration XML file for
         * android.accounts.AccountAuthenticator (e.g. res/xml/syncadapter.xml).
         * @return Handle to application's account (not guaranteed to resolve unless CreateSyncAccount()
         * has been called)
         */
        fun getAccount(accountType: String): Account {
            // Note: Normally the account name is set to the user's identity (username or email
            // address). However, since we aren't actually using any user accounts, it makes more sense
            // to use a generic string in this case.
            //
            // This string should *not* be localized. If the user switches locale, we would not be
            // able to locate the old account, and may erroneously register multiple accounts.
            val accountName = ACCOUNT_NAME
            return Account(accountName, accountType)
        }
    }

}