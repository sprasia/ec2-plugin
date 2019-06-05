package hudson.plugins.ec2;

import hudson.Extension;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

public class WindowsSshData extends AMITypeData {
    private final String sshPort;

    @DataBoundConstructor
    public WindowsSshData(String sshPort) {
        this.sshPort = sshPort;

        this.readResolve();
    }

    protected Object readResolve() {
        Jenkins.get().checkPermission(Jenkins.RUN_SCRIPTS);
        return this;
    }

    @Override
    public boolean isWindows() {
        return true;
    }

    @Override
    public boolean isUnix() {
        return false;
    }

    @Override
    public boolean isThroughSsh() {
        return true;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<AMITypeData> {
        @Override
        public String getDisplayName() {
            return "windows-ssh";
        }
    }

    public String getSshPort() {
        return sshPort == null || sshPort.isEmpty() ? "22" : sshPort;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sshPort == null) ? 0 : sshPort.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        final WindowsSshData other = (WindowsSshData) obj;
        if (StringUtils.isEmpty(sshPort)) {
            return StringUtils.isEmpty(other.sshPort);
        }
        return sshPort.equals(other.sshPort);
    }
}
