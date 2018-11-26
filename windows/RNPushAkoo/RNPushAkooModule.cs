using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Push.Akoo.RNPushAkoo
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNPushAkooModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNPushAkooModule"/>.
        /// </summary>
        internal RNPushAkooModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNPushAkoo";
            }
        }
    }
}
