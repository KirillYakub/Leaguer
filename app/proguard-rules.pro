# --- WorkManager core ---
-keep class androidx.work.impl.** { *; }
-keep class androidx.work.Configuration { *; }
-dontwarn androidx.work.**

# --- Keep your Workers (including Hilt Workers) ---
-keep class **.worker.**Worker { *; }
-keep class **.workers.**Worker { *; }

# --- AssistedInject (HiltWorker uses this internally) ---
-keep class dagger.assisted.** { *; }
-keep interface dagger.assisted.** { *; }

# --- Hilt annotations ---
-keepattributes *Annotation*
-keep class dagger.hilt.** { *; }
-keep class * extends androidx.work.ListenableWorker { *; }