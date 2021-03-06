SUMMARY = "U-boot boot scripts for AVNET devices"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS = "u-boot-mkimage-native"

inherit deploy nopackages plnx-deploy

INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = " \
            file://avnet_* \
            "
PACKAGE_ARCH = "${MACHINE_ARCH}"

do_configure[noexec] = "1"
do_install[noexec] = "1"

do_compile() {
    for file in ${WORKDIR}/avnet_*; do
        [ -e "$file" ] || continue
        mkimage -A arm -T script -C none -n "Boot script" -d "$file" $file.scr
    done
}

do_deploy() {
    install -d ${DEPLOYDIR}/avnet-boot/

    for file in ${WORKDIR}/avnet_*.scr; do
        [ -e "$file" ] || continue
        install -m 0644 $file ${DEPLOYDIR}/avnet-boot/
    done
}

addtask do_deploy after do_compile before do_build
