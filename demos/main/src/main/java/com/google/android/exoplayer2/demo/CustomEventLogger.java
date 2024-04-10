package com.google.android.exoplayer2.demo;

import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.util.EventLogger;
import java.util.ArrayList;

public class CustomEventLogger extends EventLogger {
  private final ArrayList<TSMediaFile> mMediaLoadDatas;

  public ArrayList<TSMediaFile> getMediaLoadDatas() {
    return mMediaLoadDatas;
  }

  public CustomEventLogger() {
    mMediaLoadDatas = new ArrayList<>();
  }
  @Override
  public void onLoadStarted(EventTime eventTime, LoadEventInfo loadEventInfo,
      MediaLoadData mediaLoadData) {
    super.onLoadStarted(eventTime, loadEventInfo, mediaLoadData);
    String uri = loadEventInfo.uri.toString();
    if (uri.endsWith(".ts")) {
      String[] tsUrlParts = uri.split("/");
      String tsFileName = tsUrlParts[tsUrlParts.length - 1];
      mMediaLoadDatas.add(new TSMediaFile(tsFileName, mediaLoadData.mediaStartTimeMs, mediaLoadData.mediaEndTimeMs));
    }
  }
}

class TSMediaFile {
  private String mName;
  private long mStartMs;
  private long mEndMs;

  public String getName() {
    return mName;
  }

  public long getStartMs() {
    return mStartMs;
  }

  public long getEndMs() {
    return mEndMs;
  }

  public TSMediaFile(String name, long startMs, long endMs) {
    this.mName = name;
    this.mStartMs = startMs;
    this.mEndMs = endMs;
  }

  @Override
  public String toString() {
    return "LoadedTsFile{" +
        "startMs=" + mStartMs +
        ", endMs=" + mEndMs +
        ", tsFileName='" + mName + '\'' +
        '}';
  }

  public static TSMediaFile findMediaLoadData(ArrayList<TSMediaFile> mediaLoadDataList, long timestamp) {
    for (TSMediaFile mediaLoadData : mediaLoadDataList) {
      long startTime = mediaLoadData.getStartMs();
      long endTime = mediaLoadData.getEndMs();
      if (timestamp >= startTime && timestamp <= endTime) {
        return mediaLoadData;
      }
    }
    return null; // Not found
  }
}
