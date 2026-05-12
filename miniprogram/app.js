App({
  globalData: {
    baseUrl: 'http://127.0.0.1:9007',
    userId: 1,
    activityId: 1,
    token: ''
  },
  onLaunch() {
    const token = wx.getStorageSync('token') || '';
    this.globalData.token = token;
  }
});
