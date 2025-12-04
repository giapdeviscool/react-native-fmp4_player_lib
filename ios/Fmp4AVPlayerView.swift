import UIKit
import AVFoundation
import VideoToolbox

@objcMembers
public class Fmp4AVPlayerView: UIView {

    private let displayLayer = AVSampleBufferDisplayLayer()
    private let audioplayer = AVSampleBufferAudioRenderer()
    private var controlTimebase: CMTimebase?
    
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        commonInit()
    }

    required init?(coder: NSCoder) {
        super.init(coder: coder)
        commonInit()
    }

    private func commonInit() {
        backgroundColor = UIColor.systemGray5
        displayLayer.videoGravity = .resizeAspect
        displayLayer.backgroundColor = UIColor.black.cgColor
        layer.addSublayer(displayLayer)
    }
    
    public func setStreamID(_ Id: String) {
      NativeFmp4PlayerLib.attachId(Id: Id)
      NativeFmp4PlayerLib.attachPlayer(videoplayer: displayLayer, audioplayers: audioplayer)
    }
    
    public override func layoutSubviews() {
        super.layoutSubviews()
        displayLayer.frame = bounds
    }
    
 
    

}
