/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * Presence View
 *
 * The class extends Y.View. It represents the view for buddy presence
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ErrorMessageView = Y.Base.create('errorMessageView', Y.View, [], {

    // The template property holds the contents of the #lims-error-notification-template
    // element, which will be used as the HTML template for error notifications
    // Check the templates.jspf to see all templates
    errorTemplate: Y.one('#lims-error-notification-template').get('innerHTML'),

    /**
     * Shows create error message
     *
     * @private
     */
    showErrorMessage: function () {
        // Vars
        var errorContainer = this.get('errorContainer'),
            resendButton = this.get('resendButton'),
            container = this.get('container'),
            animation,
            instance = this;

        // If the error container is already in the document do nothing
        if (!errorContainer.inDoc()) {

            // Create an instance of animation
            animation = new Y.Anim({
                node: errorContainer,
                duration: 0.3,
                from: {opacity: 0},
                to: {opacity: 1}
            });

            // Opacity needs to be set to zero otherwise there wil
            // be a weird blink effect
            errorContainer.setStyle('opacity', 0);

            // Attach click on resend button event
            errorContainer.one('.resend-button').on('click', function (event) {
                event.preventDefault();

                // Prevent user to click on preloader more than once
                if (!resendButton.hasClass('preloader')) {
                    // Add preloader to the resend button
                    resendButton.addClass('preloader');
                    // Fire click event
                    instance.fire('resendButtonClick');
                }
            });

            // Add error to the container
            container.append(errorContainer);

            // Run the effect animation
            animation.run();
        }

        // It is possible that resend button was clicked thus it was transformed to the preloader.
        // Remove the preloader class so it can be the resend button again.
        errorContainer.one('.resend-button').removeClass('preloader');
    },

    /**
     * Hides create error message
     *
     * @private
     */
    hideErrorMessage: function () {
        // Vars
        var errorContainer = this.get('errorContainer'),
            animation;

        // Run the animation only if the error container is in DOM
        if (errorContainer.inDoc()) {

            // Create an instance of animation
            animation = new Y.Anim({
                node: errorContainer,
                duration: 0.3,
                from: {opacity: 1},
                to: {opacity: 0}
            });

            // Listen to the end of the animation
            animation.on('end', function () {
                // Remove the error node from DOM
                animation.get('node').remove();
            });

            // Run the animation
            animation.run();
        }
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        /**
         * Container attached to error message
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Error container that holds error content
         *
         * {Node}
         */
        errorContainer: {
            valueFn: function () {
                // Vars
                var errorMessage = this.get('errorMessage');
                // Create from template
                return Y.Node.create(Y.Lang.sub(this.errorTemplate, {
                    errorMessage: errorMessage
                }));
            }
        },

        /**
         * Resend button node
         *
         * {Node}
         */
        resendButton: {
            valueFn: function () {
                return this.get('errorContainer').one('.resend-button');
            }
        },

        /**
         * Error message
         *
         * {string}
         */
        errorMessage: {
            value: null // to be set
        }
    }
});

