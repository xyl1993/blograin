/**
 * 自定义jquery插件
 * 此种写法基于jqeury ui格式，需提前引入jqueryui
 */
 (function ($) {
    /**
     * 图片预览
     */
    $.widget('ui.imgSee', {
        _create: function () {
            var thisImg = this.element.find('>img'),
                _this = this;
            thisImg.css({top: '', left: '', opacity: '0.0'})
            thisImg.on('load', function () {
                /*alert('load');*/
                _this.position();
            });
            this._on($(window), {
                resize: function () {
                    _this.position();
                }
            });
            this._on(this.element.find('.img-see-close'), {
                click: function () {
                    _this.destroy();
                    this.element.remove();
                }
            });
            this.element.find('.left').click(function () {
                thisImg.css({top: '', left: '', opacity: '0.0'});
            });
            this.element.find('.right').click(function () {
                thisImg.css({top: '', left: '', opacity: '0.0'});
            })
            this.position();
        },
        position: function () {
            var thisImg = this.element.find('>img').css({width: '', height: ''});
            winH = $(window).height(),
                winW = $(window).width(),
                imgH = thisImg.height(),
                imgW = thisImg.width();
            if (imgW >= winW && imgH < winH) {
                thisImg.css({width: winW - 10, height: ''});
            }
            else if (imgH >= winH && imgW < winW) {
                thisImg.css({height: winH - 10, width: ''});
            }
            else if (imgH >= winH && imgW >= winW) {
                if (imgH >= imgW) {
                    thisImg.css({height: winH - 10, width: ''});
                } else {
                    thisImg.css({width: winW - 10, height: ''});
                }
            }
            thisImg.css({top: '', left: '', opacity: '0.0'})
                .position({
                    my: 'center center',
                    at: 'center center',
                    of: this.element

                }).css({opacity: 1});
        }
    });

})(jQuery);