// 活动分类
export function offerCategory (key) {
    if(key === 'BANK') {
        return '银行'
    } else if(key === 'CREDIT_CARD') {
        return '信用卡'
    } else if(key === 'BROKER') {
        return '券商'
    }

    return '-';
}


// 用户登录类型
export function loginType (key) {
    if(key === 'GOOGLE') {
        return 'Google'
    } else if(key === 'APPLE') {
        return 'Apple'
    } else if(key === 'GUEST') {
        return '游客'
    }

    return '-';
}

// 用户参与活动状态
export function userOfferState (key) {
    if(key === 'APPLYING') {
        return '申请中'
    } else if(key === 'COMPLETED') {
        return '已完成'
    } else if(key === 'REJECTED') {
        return '已拒绝'
    } else if(key === 'REWARDING') {
        return '领取奖励中'
    }

    return '-';
}
