
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;
import org.nlogo.api.ViewSettings;
import org.nlogo.api.World;
import org.nlogo.app.App;
import org.nlogo.nvm.ExtensionContext;

import de.erichseifert.vectorgraphics2d.EPSGraphics2D;
import de.erichseifert.vectorgraphics2d.PDFGraphics2D;
import de.erichseifert.vectorgraphics2d.SVGGraphics2D;

/**
 * @author Ahmad Esmaeili 
 *
 * Created on Dec 9, 2015
 */


public class ExportVector extends DefaultCommand {

	@Override
	public void perform(Argument[] params, Context context)
			throws ExtensionException, LogoException {

		String vectorFormat = params[0].getString().toLowerCase(Locale.ENGLISH);

		if (vectorFormat.equals("eps") || vectorFormat.equals("pdf")
				|| vectorFormat.equals("svg")) {
			File epsFile;
			String imgDir = App.app().workspace().getModelDir();
			// In case the model is not saved, the image is saved in user's
			// directory
			if (imgDir == null)
				imgDir = System.getProperty("user.home");
			try {
				epsFile = new File(imgDir + File.separatorChar
						+ params[2].getString() + "." + vectorFormat);
			} catch (LogoException e) {
				throw new ExtensionException(e.getMessage());
			}
			FileOutputStream finalFile = null;
			try {
				finalFile = new FileOutputStream(epsFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			final BufferedImage bi = ((ExtensionContext) context).workspace()
					.exportView();
			final World w = ((ExtensionContext) context).workspace().world();
			ViewSettings vs = App.app().workspace().view;

			Graphics2D gVector = null;

			if (vectorFormat.equals("eps")) {
				gVector = new EPSGraphics2D(0, 0, bi.getWidth()
						* params[1].getIntValue(), bi.getHeight()
						* params[1].getIntValue());
			} else if (vectorFormat.equals("pdf")) {
				gVector = new PDFGraphics2D(0, 0, bi.getWidth()
						* params[1].getIntValue(), bi.getHeight()
						* params[1].getIntValue());
			} else {
				gVector = new SVGGraphics2D(0, 0, bi.getWidth()
						* params[1].getIntValue(), bi.getHeight()
						* params[1].getIntValue());
			}
			// The following code block tries to scale the image
			gVector.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
					RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			gVector.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			gVector.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
					RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			gVector.setRenderingHint(RenderingHints.KEY_DITHERING,
					RenderingHints.VALUE_DITHER_ENABLE);
			gVector.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
					RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			gVector.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			gVector.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			gVector.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
					RenderingHints.VALUE_STROKE_PURE);
			gVector.setTransform(AffineTransform.getScaleInstance(
					params[1].getIntValue(), params[1].getIntValue()));
			org.nlogo.render.Renderer r = new org.nlogo.render.Renderer(w);
			r.exportView(gVector, vs);
			try {
				if (vectorFormat.equals("eps")) {
					finalFile.write(((EPSGraphics2D) gVector).getBytes());
				} else if (vectorFormat.equals("pdf")) {
					finalFile.write(((PDFGraphics2D) gVector).getBytes());
				} else {
					finalFile.write(((SVGGraphics2D) gVector).getBytes());
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					finalFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			throw new ExtensionException(
					"\'"
							+ params[0].getString()
							+ "\' image format is not supported. Please use any of EPS, PDF, or SVG instead.");
		}
	}

	public Syntax getSyntax() {
		return Syntax.commandSyntax(new int[] { Syntax.StringType(),
				Syntax.NumberType(), Syntax.StringType() });

	}
}
