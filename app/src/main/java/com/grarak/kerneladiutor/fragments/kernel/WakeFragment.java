/*
 * Copyright (C) 2015 Willi Ye
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.grarak.kerneladiutor.fragments.kernel;

import android.os.Bundle;

import com.grarak.kerneladiutor.R;
import com.grarak.kerneladiutor.elements.cards.PopupCardView;
import com.grarak.kerneladiutor.elements.cards.SeekBarCardView;
import com.grarak.kerneladiutor.elements.cards.SwitchCardView;
import com.grarak.kerneladiutor.fragments.RecyclerViewFragment;
import com.grarak.kerneladiutor.utils.kernel.Wake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willi on 02.01.15.
 */
public class WakeFragment extends RecyclerViewFragment implements PopupCardView.DPopupCard.OnDPopupCardListener,
        SeekBarCardView.DSeekBarCard.OnDSeekBarCardListener,
        SwitchCardView.DSwitchCard.OnDSwitchCardListener {

    //TODO add feather X/Y and DT2W time tunables

    private PopupCardView.DPopupCard mDt2wCard;
    private PopupCardView.DPopupCard mS2wCard;
    private SwitchCardView.DSwitchCard mLenientCard;
    private PopupCardView.DPopupCard mT2wCard;
    private PopupCardView.DPopupCard mWakeMiscCard;
    private PopupCardView.DPopupCard mSleepMiscCard;
    private PopupCardView.DPopupCard mDt2sCard;
    private SwitchCardView.DSwitchCard[] mGestureCards;
    private SwitchCardView.DSwitchCard mCameraGestureCard;
    private SwitchCardView.DSwitchCard mPocketModeCard;

    private SeekBarCardView.DSeekBarCard mWakeTimeoutCard, mDT2WTimeBetweenTapsCard;
    private SwitchCardView.DSwitchCard mPowerKeySuspendCard;

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        if (Wake.hasDt2w()) dt2wInit();
        if (Wake.hasS2w()) s2wInit();
        if (Wake.hasLenient()) lenientInit();
        if (Wake.hasT2w() && !Wake.hasDt2w()) t2wInit();
        if (Wake.hasWakeMisc()) wakeMiscInit();
        if (Wake.hasSleepMisc()) sleepMiscInit();
        if (Wake.hasDt2s()) dt2sInit();
        if (Wake.hasGesture()) gestureInit();
        if (Wake.hasCameraGesture()) cameraGestureInit();
        if (Wake.hasPocketMode()) pocketModeInit();
        if (Wake.hasWakeTimeout()) wakeTimeoutInit();
        if (Wake.hasPowerKeySuspend()) powerKeySuspendInit();
        if (Wake.hasDT2WTimeBetweenTaps()) DT2WTimeBetweenTapsInit();
    }

    private void dt2wInit() {
        mDt2wCard = new PopupCardView.DPopupCard(Wake.getDt2wMenu(getActivity()));
        mDt2wCard.setTitle(getString(R.string.dt2w));
        mDt2wCard.setDescription(getString(R.string.dt2w_summary));
        mDt2wCard.setItem(Wake.getDt2wValue());
        mDt2wCard.setOnDPopupCardListener(this);

        addView(mDt2wCard);
    }

    private void s2wInit() {
        mS2wCard = new PopupCardView.DPopupCard(Wake.getS2wMenu(getActivity()));
        mS2wCard.setTitle(getString(R.string.s2w));
        mS2wCard.setDescription(getString(R.string.s2w_summary));
        mS2wCard.setItem(Wake.getS2wValue());
        mS2wCard.setOnDPopupCardListener(this);

        addView(mS2wCard);
    }

    private void lenientInit() {
        mLenientCard = new SwitchCardView.DSwitchCard();
        mLenientCard.setTitle(getString(R.string.lenient));
        mLenientCard.setDescription(getString(R.string.lenient_summary));
        mLenientCard.setChecked(Wake.isLenientActive());
        mLenientCard.setOnDSwitchCardListener(this);

        addView(mLenientCard);
    }

    private void t2wInit() {
        mT2wCard = new PopupCardView.DPopupCard(Wake.getT2wMenu(getActivity()));
        mT2wCard.setTitle(getString(R.string.t2w));
        mT2wCard.setDescription(getString(R.string.t2w_summary));
        mT2wCard.setItem(Wake.getT2w());
        mT2wCard.setOnDPopupCardListener(this);

        addView(mT2wCard);
    }

    private void wakeMiscInit() {
        mWakeMiscCard = new PopupCardView.DPopupCard(Wake.getWakeMiscMenu(getActivity()));
        mWakeMiscCard.setDescription(getString(R.string.wake));
        mWakeMiscCard.setItem(Wake.getWakeMisc());
        mWakeMiscCard.setOnDPopupCardListener(this);

        addView(mWakeMiscCard);
    }

    private void sleepMiscInit() {
        mSleepMiscCard = new PopupCardView.DPopupCard(Wake.getSleepMiscMenu(getActivity()));
        mSleepMiscCard.setDescription(getString(R.string.sleep));
        mSleepMiscCard.setItem(Wake.getSleepMisc());
        mSleepMiscCard.setOnDPopupCardListener(this);

        addView(mSleepMiscCard);
    }

    private void dt2sInit() {
        mDt2sCard = new PopupCardView.DPopupCard(Wake.getDt2sMenu(getActivity()));
        mDt2sCard.setTitle(getString(R.string.dt2s));
        mDt2sCard.setDescription(getString(R.string.dt2s_summary));
        mDt2sCard.setItem(Wake.getDt2sValue());
        mDt2sCard.setOnDPopupCardListener(this);

        addView(mDt2sCard);
    }

    private void gestureInit() {
        List<String> gestures = Wake.getGestures(getActivity());
        mGestureCards = new SwitchCardView.DSwitchCard[gestures.size()];
        for (int i = 0; i < mGestureCards.length; i++) {
            mGestureCards[i] = new SwitchCardView.DSwitchCard();
            mGestureCards[i].setDescription(gestures.get(i));
            mGestureCards[i].setChecked(Wake.isGestureActive(i));
            mGestureCards[i].setOnDSwitchCardListener(this);

            addView(mGestureCards[i]);
        }
    }

    private void cameraGestureInit() {
        mCameraGestureCard = new SwitchCardView.DSwitchCard();
        mCameraGestureCard.setTitle(getString(R.string.camera_gesture));
        mCameraGestureCard.setDescription(getString(R.string.camera_gesture_summary));
        mCameraGestureCard.setChecked(Wake.isCameraGestureActive());
        mCameraGestureCard.setOnDSwitchCardListener(this);

        addView(mCameraGestureCard);
    }

    private void pocketModeInit() {
        mPocketModeCard = new SwitchCardView.DSwitchCard();
        mPocketModeCard.setTitle(getString(R.string.pocket_mode));
        mPocketModeCard.setDescription(getString(R.string.pocket_mode_summary));
        mPocketModeCard.setChecked(Wake.isPocketModeActive());
        mPocketModeCard.setOnDSwitchCardListener(this);

        addView(mPocketModeCard);
    }

    private void wakeTimeoutInit() {
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.disabled));
        for (int i = 1; i <= Wake.getWakeTimeoutMax(); i++)
            list.add(i + getString(R.string.min));

        mWakeTimeoutCard = new SeekBarCardView.DSeekBarCard(list);
        mWakeTimeoutCard.setTitle(getString(R.string.wake_timeout));
        mWakeTimeoutCard.setDescription(getString(R.string.wake_timeout_summary));
        mWakeTimeoutCard.setProgress(Wake.getWakeTimeout());
        mWakeTimeoutCard.setOnDSeekBarCardListener(this);

        addView(mWakeTimeoutCard);
    }

    private void powerKeySuspendInit() {
        mPowerKeySuspendCard = new SwitchCardView.DSwitchCard();
        mPowerKeySuspendCard.setTitle(getString(R.string.power_key_suspend));
        mPowerKeySuspendCard.setDescription(getString(R.string.power_key_suspend_summary));
        mPowerKeySuspendCard.setChecked(Wake.isPowerKeySuspendActive());
        mPowerKeySuspendCard.setOnDSwitchCardListener(this);

        addView(mPowerKeySuspendCard);
    }

    private void DT2WTimeBetweenTapsInit() {
        List<String> list = new ArrayList<>();
        for (int i = 25; i <= 100; i++)
            list.add(i * 10 + getString(R.string.ms));

        mDT2WTimeBetweenTapsCard = new SeekBarCardView.DSeekBarCard(list);
        mDT2WTimeBetweenTapsCard.setTitle(getString(R.string.wake_dt2w_timebetweentaps));
        mDT2WTimeBetweenTapsCard.setDescription(getString(R.string.wake_dt2w_timebetweentaps_summary));
        mDT2WTimeBetweenTapsCard.setProgress(Wake.getDT2WTimeBetweenTaps() - 25);
        mDT2WTimeBetweenTapsCard.setOnDSeekBarCardListener(this);

        addView(mDT2WTimeBetweenTapsCard);


    }

    @Override
    public void onItemSelected(PopupCardView.DPopupCard dPopupCard, int position) {
        if (dPopupCard == mDt2wCard) Wake.setDt2w(position, getActivity());
        else if (dPopupCard == mS2wCard) Wake.setS2w(position, getActivity());
        else if (dPopupCard == mT2wCard) Wake.setT2w(position, getActivity());
        else if (dPopupCard == mWakeMiscCard) Wake.setWakeMisc(position, getActivity());
        else if (dPopupCard == mSleepMiscCard) Wake.setSleepMisc(position, getActivity());
        else if (dPopupCard == mDt2sCard) Wake.setDt2s(position, getActivity());
    }

    @Override
    public void onChanged(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
    }

    @Override
    public void onStop(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
        if (dSeekBarCard == mWakeTimeoutCard) Wake.setWakeTimeout(position, getActivity());
        else if (dSeekBarCard == mDT2WTimeBetweenTapsCard) Wake.setDT2WTimeBetweenTaps(position + 25, getActivity());
    }

    @Override
    public void onChecked(SwitchCardView.DSwitchCard dSwitchCard, boolean checked) {
        if (dSwitchCard == mLenientCard)
            Wake.activateLenient(checked, getActivity());
        else if (dSwitchCard == mCameraGestureCard)
            Wake.activateCameraGesture(checked, getActivity());
        else if (dSwitchCard == mPocketModeCard)
            Wake.activatePocketMode(checked, getActivity());
        else if (dSwitchCard == mPowerKeySuspendCard)
            Wake.activatePowerKeySuspend(checked, getActivity());
        else {
            for (int i = 0; i < mGestureCards.length; i++)
                if (dSwitchCard == mGestureCards[i]) {
                    Wake.activateGesture(checked, i, getActivity());
                    return;
                }
        }
    }

}
