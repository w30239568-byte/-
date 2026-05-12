const { request } = require('../../../utils/request');

Page({
  data: {
    loading: true,
    detail: {},
    canSignUp: true
  },

  onShow() {
    this.loadData();
  },

  async loadData() {
    const app = getApp();
    this.setData({ loading: true });
    try {
      const detail = await request({
        url: '/api/activity/getActivityDetail',
        data: { activityId: app.globalData.activityId, userId: app.globalData.userId }
      });
      this.setData({
        detail: detail || {},
        canSignUp: !(detail && detail.ifSign)
      });
    } finally {
      this.setData({ loading: false });
    }
  },

  goCalendar() {
    wx.navigateTo({ url: '/pages/checkin/calendar/index' });
  },

  async signUp() {
    const app = getApp();
    try {
      await request({
        url: '/api/activity/activitySignUp',
        data: { activityId: app.globalData.activityId, userId: app.globalData.userId }
      });
      wx.showToast({ title: '报名成功', icon: 'success' });
      this.loadData();
    } catch (e) {}
  }
});
